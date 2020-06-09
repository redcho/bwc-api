package com.bigcode.crawler;

import com.bigcode.handler.GCSHandler;
import com.bigcode.model.Realm;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    private static Logger log = LogManager.getLogger(Crawler.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        List<Realm> realms = getRealms(downloadRealmListFromGCS());
    }

    public static String downloadRealmListFromAPI(){
        WowCrawler wowCrawler = new WowCrawler();
        return wowCrawler.downloadRealms();
    }

    public static String downloadRealmListFromGCS(){
        return GCSHandler.downloadString("wow-static", "realms.json");
    }

    public static List<Realm> getRealms(String realmListStr){
        Realm[] list = new Realm[0];
        try {
            list = mapper.readValue(realmListStr, Realm[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<Realm> realmList = Arrays.asList(list);
//        realmList.forEach(System.out::println);
        return realmList;
    }

    public static void uploadRealmListToGCS() {
        try {
            String response = downloadRealmListFromAPI();

            JsonNode rootNode = mapper.readTree(response);
            String realmsArrStr = rootNode.path("realms").toString();

            GCSHandler.uploadString("wow-static", "realms.json", realmsArrStr);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

}
