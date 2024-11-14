import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class MultiThreadWebCrawler {
    private ThreadPoolExecutor executorService;
    private Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());
    private Map<Integer, ArrayList<String>> scrapedUrls = new ConcurrentHashMap<>();
    private int maxDepth;


    public MultiThreadWebCrawler(int maxThreads, int maxDepth) {
        this.executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(maxThreads);
        this.maxDepth = maxDepth;
    }


    public void crawl(String startUrl) {
        submitTask(startUrl, 0);

        try {
            // when i use the first while even if the number of tasks and active threads is 0,
            // the main threads doesn't stop executing the while loop.
            //while (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {

            // using the second while is ok
            while (executorService.getActiveCount() > 0 || !executorService.getQueue().isEmpty()) {
                //System.out.println("Active threads: " + executorService.getActiveCount());
                //System.out.println("Pending tasks: " + executorService.getQueue().size());
                Thread.sleep(100);
            }

            //System.out.print("\n\t\t\tMain thread has been unblocked.\n");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
        //writeUrlsToJsonFile();
    }


    private void submitTask(String url, int depth) {
        if (visitedUrls.contains(url) || depth >= maxDepth) return;

        visitedUrls.add(url);
        executorService.submit(() -> crawlPage(url, depth));
    }

    private void crawlPage(String url, int depth) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements links = document.select("a[href]");
            ArrayList<String> httpUrls = new ArrayList<>();
            //int i = 0;

            for (Element link : links) {
                //if (i == 2) break;

                String nextUrl = link.absUrl("href");

                if (nextUrl.startsWith("http")) {
                    //i++;
                    httpUrls.add(nextUrl);
                    submitTask(nextUrl, depth + 1); // Submit deeper URLs
                }
            }

            synchronized (scrapedUrls) {
                scrapedUrls.computeIfAbsent(depth, k -> new ArrayList<>()).addAll(httpUrls);
                //System.out.print("\nThread: " + Thread.currentThread().getName() + " has added httpUrls at depth: '" + depth + "'.");
                //System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Failed to fetch: " + url);
        }
    }

    private void writeUrlsToJsonFile() {
        JSONObject jsonMap = new JSONObject();
        for (Integer depth : scrapedUrls.keySet())
            jsonMap.put(depth.toString(), new JSONArray(scrapedUrls.get(depth)));

        try (FileWriter file = new FileWriter("scrapedUrls2.json")) {
            file.write(jsonMap.toString(4));
            System.out.println("The urls where successfully written in 'scrapedUrls2.json'.");
        } catch (IOException e) {
            System.err.println("Failed to write to file");
        }
    }
}
