package com.bigcode.crawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class Crawler {

    Logger log = LogManager.getLogger(Crawler.class);

    public static void main(String[] args) throws IOException {
        ApiCrawler crawler = new ApiCrawler();

        String relativePath = "/data/wow/realm/index";
        try {
            crawler.getDataFromRelativePath(relativePath, null);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
