public class Main {

    //private static final String URL = "https://en.wikipedia.org";
    //private static final String URL = "https://stackoverflow.com/";
    private static final String URL = "https://www.geeksforgeeks.org/";


    public static void main(String[] args) {
        /*********** Single-thread ***********/
        /*
        long startTime = System.currentTimeMillis();

        SingleThreadWebCrawler crawler1 = new SingleThreadWebCrawler(3);
        crawler1.crawl(URL);
        //crawler1.writeUrlsToJsonFile();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Time: " + duration + " ms.");
        */


        /*********** Multi-threading ***********/
        //startTime = System.currentTimeMillis();

        MultiThreadWebCrawler crawler2 = new MultiThreadWebCrawler(5, 2);
        crawler2.crawl(URL);

        /*endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("Time: " + duration + " ms.");
         */
    }
}
