import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class MultiThreadWebCrawler {
    private ExecutorService executorService;
    private Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());
    private Map<Integer, List<String>> scrapedUrls = new ConcurrentHashMap<>();
    private int maxDepth;

    public MultiThreadWebCrawler(int maxThreads, int maxDepth) {
        this.executorService = Executors.newFixedThreadPool(maxThreads);
        this.maxDepth = maxDepth;
    }

    public void crawl(String startUrl) {
        submitTask(startUrl, 0);
        try {
            while (Thread.activeCount() > 1) {
                Thread.sleep(100); // Waiting for all threads to finish
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writeUrlsToJsonFile();
    }

    private void submitTask(String url, int depth) {
        if (visitedUrls.contains(url) || depth > maxDepth) return;
        visitedUrls.add(url);
        executorService.submit(() -> crawlPage(url, depth));
    }

    private void crawlPage(String url, int depth) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements links = document.select("a[href]");
            List<String> httpUrls = new ArrayList<>();

            for (Element link : links) {
                String nextUrl = link.absUrl("href");
                if (nextUrl.startsWith("http")) {
                    httpUrls.add(nextUrl);
                    submitTask(nextUrl, depth + 1); // Submit deeper URLs
                }
            }

            synchronized (scrapedUrls) {
                scrapedUrls.computeIfAbsent(depth, k -> new ArrayList<>()).addAll(httpUrls);
            }
        } catch (IOException e) {
            System.err.println("Failed to fetch: " + url);
        }
    }

    private void writeUrlsToJsonFile() {
        JSONObject jsonMap = new JSONObject();
        for (Integer depth : scrapedUrls.keySet()) {
            jsonMap.put(depth.toString(), new JSONArray(scrapedUrls.get(depth)));
        }

        try (FileWriter file = new FileWriter("scrapedUrls.json")) {
            file.write(jsonMap.toString(4)); // Pretty print with 4 spaces
        } catch (IOException e) {
            System.err.println("Failed to write to file");
        }
    }
}
