package com.wjy35.wij.util.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BOJCrawler {
    private static final String BOJ_URL_PREFIX = "https://www.acmicpc.net/problem/";
    private static final String DATA_CLASS = "sampledata";
    private static final String INPUT_ID_PREFIX = "sample-input-";
    private static final String OUTPUT_ID_PREFIX = "sample-output-";

    public static List<BOJCrawlResult> crawlAllBy(String problemNumber) throws IOException {
        Document document = Jsoup.connect(BOJ_URL_PREFIX+problemNumber).get();
        Elements elements = document.getElementsByClass(DATA_CLASS);

        int size = elements.size();
        List<BOJCrawlResult> crawlResultList = new ArrayList<>(size/2);
        for(int i=0; i<size; i+=2){
            String input = elements.get(i).text();
            String output = elements.get(i+1).text();
            crawlResultList.add(new BOJCrawlResult(i/2,input,output));
        }

        return crawlResultList;
    }

    public static BOJCrawlResult crawlByProblemNumberAndIOFileNumber(String problemNumber, int ioFileNumber) throws IOException {
        ioFileNumber++;

        Document document = Jsoup.connect(BOJ_URL_PREFIX+problemNumber).get();

        Element inputElement = document.getElementById(INPUT_ID_PREFIX+problemNumber);
        Element outputElement = document.getElementById(OUTPUT_ID_PREFIX+problemNumber);

        return new BOJCrawlResult(ioFileNumber,inputElement.text(),outputElement.text());
    }
}
