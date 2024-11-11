import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;


public class SingleThreadWebCrawler {
    private final int maxDepth;
    private HashMap<Integer, ArrayList<String>> scrapedUrls;
    private HashSet<String> visitedUrls;


    public SingleThreadWebCrawler(int maxDepth) {
        this.maxDepth = maxDepth;

        scrapedUrls = new HashMap<>();
        visitedUrls = new HashSet<>();
    }


    public void crawl(String url) {
        crawlRecursively(url, 0);
    }


    private void crawlRecursively(String url, int depth) {
        if (depth == maxDepth) return;

        try {
            Document document = Jsoup.connect(url).get();
            // System.out.println("\ndepth = " + depth);
            // System.out.println("Visited url: " + url);

            Elements extractedLinks = document.select("a[href]");
            ArrayList<String> httpUrls = new ArrayList<>();

            for (Element link : extractedLinks) {
                String nextUrl = link.absUrl("href");

                if (nextUrl.startsWith("http") && !visitedUrls.contains(nextUrl)) {
                    visitedUrls.add(nextUrl);
                    httpUrls.add(nextUrl);
                    crawlRecursively(link.absUrl("href"), depth + 1);
                }
            }

            scrapedUrls.put(depth, httpUrls);
        }
        catch (IOException e) {
            System.err.println("Error occurred while fetching the URL: " + url + ". " + e.getMessage());
        }
    }


    public void writeUrlsToJsonFile() {
        JSONObject jsonMap = new JSONObject();

        for (Integer key : scrapedUrls.keySet())
            jsonMap.put(key.toString(), new JSONArray(scrapedUrls.get(key)));

        String fileName = "ScrapedUrls1.json";

        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(jsonMap.toString(4));
            fw.flush();
        }
        catch (IOException e) {
            System.err.println("Error occured while trying to write to " + fileName + "." + e.getMessage());
        }
    }
}
