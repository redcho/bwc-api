package com.bigcode.crawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;

public class WowCrawler {
    private static Logger log = LogManager.getLogger(WowCrawler.class);

    ApiCrawler crawler;

    public WowCrawler(){
        crawler = new ApiCrawler();
    }

    public String downloadRealms() {
        try {
            String relativePath = "/data/wow/realm/index";
            return crawler.getDataFromRelativePath(relativePath, null);
        }catch (Exception ex){
            log.error(ex);
            return null;
        }
    }
}
