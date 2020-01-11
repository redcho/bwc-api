package com.bigcode.crawler;

import com.bigcode.model.Realm;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Crawler {

    Logger log = LogManager.getLogger(Crawler.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        ApiCrawler crawler = new ApiCrawler();

        String relativePath = "/data/wow/realm/index";
        try {
            String response = crawler.getDataFromRelativePath(relativePath, null);

            JsonNode rootNode = mapper.readTree(response);
            Realm[] list = mapper.readValue(rootNode.path("realms").toString(), Realm[].class);

            ArrayList<Realm> realmList = new ArrayList();
            realmList.addAll(Arrays.asList(list));
//            realmList.forEach(System.out::println);



        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

}
