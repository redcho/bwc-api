package com.bigcode.crawler;

import com.bigcode.handler.GCSHandler;
import com.bigcode.model.Realm;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.api.client.util.Charsets.UTF_8;


public class Crawler {

    Logger log = LogManager.getLogger(Crawler.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        ApiCrawler crawler = new ApiCrawler();

        String relativePath = "/data/wow/realm/index";
        try {
//            String response = crawler.getDataFromRelativePath(relativePath, null);
//
//            JsonNode rootNode = mapper.readTree(response);
//            String realmsArrStr = rootNode.path("realms").toString();
//            Realm[] list = mapper.readValue(realmsArrStr, Realm[].class);

//            ArrayList<Realm> realmList = new ArrayList();
//            realmList.addAll(Arrays.asList(list));
//            realmList.forEach(System.out::println);

//            GCSHandler.uploadString("wow-static", "realms.json", realmsArrStr);
            GCSHandler.downloadString("wow-static", "realms.json");

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

}
