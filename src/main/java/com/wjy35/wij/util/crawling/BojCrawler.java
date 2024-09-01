package com.wjy35.wij.util.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BojCrawler {
    private static final String BOJ_URL_PREFIX = "https://www.acmicpc.net/problem/";

    private static final String DATA_CLASS = "sampledata";
    private static final String INPUT_ID_PREFIX = "sample-input-";
    private static final String OUTPUT_ID_PREFIX = "sample-output-";

    public static List<BojCrawlResult> crawlAll(String problemNumber) throws IOException {
        Document document = Jsoup.connect(BOJ_URL_PREFIX+problemNumber).get();
        Elements elements = document.getElementsByClass(DATA_CLASS);

        int size = elements.size();
        List<BojCrawlResult> crawlResultList = new ArrayList<>(size/2);
        for(int i=0; i<size; i+=2){
            String input = elements.get(i).text();
            String output = elements.get(i+1).text();
            crawlResultList.add(new BojCrawlResult(i/2,input,output));
        }

        return crawlResultList;
    }

    public static BojCrawlResult crawlWithFileNumber(String problemNumber,int fileNumber){
        fileNumber++;
        Document document = null;
        try {
            document = Jsoup.connect(BOJ_URL_PREFIX+problemNumber).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element inputElement = document.getElementById(INPUT_ID_PREFIX+problemNumber);
        Element outputElement = document.getElementById(OUTPUT_ID_PREFIX+problemNumber);

        return new BojCrawlResult(fileNumber,inputElement.text(),outputElement.text());
    }

}
