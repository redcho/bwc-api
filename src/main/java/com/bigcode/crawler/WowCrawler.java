package com.bigcode.crawler;

import com.bigcode.proto.LeaderboardProtos;
import com.bigcode.proto.RealmProtos;
import com.bigcode.proto.SeasonProtos;
import com.bigcode.util.ProtoUtils;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WowCrawler {
    private static Logger log = LogManager.getLogger(WowCrawler.class);

    ApiCrawler crawler;

    public WowCrawler(){
        crawler = new ApiCrawler();
    }

    public RealmProtos.RealmArr downloadRealms() {
        String relativePath = "/data/wow/realm/index";
        String response = crawler.getDataFromRelativePath(relativePath, null);

//      JsonNode rootNode = mapper.readTree(response);
//      String realmsArrStr = rootNode.path("realms").toString();

        return ProtoUtils.fromJson(response, RealmProtos.RealmArr.newBuilder());
    }

    public SeasonProtos.SeasonArr downloadPvPSeasonIndex(){
        String relativePath = "/data/wow/pvp-season/index";
        String response = crawler.getDataFromRelativePath(relativePath, null);
        return ProtoUtils.fromJson(response, SeasonProtos.SeasonArr.newBuilder());
    }

    public String downloadPvpSeasons(){
        String relativePath = "/data/wow/pvp-season/29"; //"/pvp-leaderboard/index";
        String response = crawler.getDataFromRelativePath(relativePath, null);
        System.out.println(response);
        return null;
    }

    public LeaderboardProtos.LeaderboardArr getLeaderboardsIndex(int seasonId){
        String relativePath = String.format("/data/wow/pvp-season/%d/pvp-leaderboard/index", seasonId);
        String response = crawler.getDataFromRelativePath(relativePath, null);

        return ProtoUtils.fromJson(response, LeaderboardProtos.LeaderboardArr.newBuilder());
    }

    public String getleaderboard(int seasonId, String pvpBracket){
        String relativePath = String.format("/data/wow/pvp-season/%d/pvp-leaderboard/%s", seasonId, pvpBracket);
        String response = crawler.getDataFromRelativePath(relativePath, null);

        System.out.println(response);
        return null;
    }
}
