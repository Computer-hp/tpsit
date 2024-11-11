public class Main {

    //private static final String URL = "https://en.wikipedia.org";
    //private static final String URL = "https://stackoverflow.com/";
    private static final String URL = "https://www.geeksforgeeks.org/";


    public static void main(String[] args) {
        /*SingleThreadWebCrawler crawler1 = new SingleThreadWebCrawler(3);
        crawler1.crawl(URL);
        crawler1.writeUrlsToJsonFile();*/

        MultiThreadWebCrawler crawler2 = new MultiThreadWebCrawler(5, 3);
        crawler2.crawl(URL);
    }
}
