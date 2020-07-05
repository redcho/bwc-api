package com.bigcode.crawler;

import com.bigcode.handler.GCSHandler;
import com.bigcode.proto.LeaderboardProtos;
import com.bigcode.proto.RealmProtos;
import com.bigcode.proto.SeasonProtos;
import com.bigcode.util.ProtoUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.*;
import com.google.protobuf.util.JsonFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.google.api.client.util.Charsets.UTF_8;


public class Crawler {

    private static Logger log = LogManager.getLogger(Crawler.class);
    private static WowCrawler wowCrawler = new WowCrawler();

    public static void main(String[] args) throws IOException {
//        RealmProtos.RealmArr realmArr = ProtoUtils.fromJson(downloadRealmListFromGCS(), RealmProtos.RealmArr.newBuilder());

        SeasonProtos.SeasonArr seasonArr = ProtoUtils.fromJson(GCSHandler.downloadString("wow-static", "seasons.json"), SeasonProtos.SeasonArr.newBuilder());
        SeasonProtos.Season season = seasonArr.getSeasons(seasonArr.getSeasonsCount() - 1);

        int activeSeasonId = season.getId();

        LeaderboardProtos.LeaderboardArr leaderboardArr = ProtoUtils.fromJson(GCSHandler.downloadString("wow-static", String.format("season_%d_leaderboards.json", activeSeasonId)), LeaderboardProtos.LeaderboardArr.newBuilder());

        for(LeaderboardProtos.Leaderboard leaderboard : leaderboardArr.getLeaderboardsList()){
            wowCrawler.getleaderboard(activeSeasonId, leaderboard.getName());
        }
    }

    public static String downloadRealmListFromGCS(){
        return GCSHandler.downloadString("wow-static", "realms.json");
    }

    public static void uploadLatestLeaderboardsToGCS(){
        SeasonProtos.SeasonArr seasonArr = ProtoUtils.fromJson(GCSHandler.downloadString("wow-static", "seasons.json"), SeasonProtos.SeasonArr.newBuilder());
        int activeSeasonId = seasonArr.getSeasons(seasonArr.getSeasonsCount() - 1).getId();
        GCSHandler.uploadString("wow-static", String.format("season_%d_leaderboards.json", activeSeasonId), Objects.requireNonNull(ProtoUtils.toJson(wowCrawler.getLeaderboardsIndex(activeSeasonId))));
    }

    public static void uploadRealmListToGCS() {
        GCSHandler.uploadString("wow-static", "realms.json", Objects.requireNonNull(ProtoUtils.toJson(wowCrawler.downloadRealms())));
    }

    public static void uploadSeasonListToGCS(){
        GCSHandler.uploadString("wow-static", "seasons.json", Objects.requireNonNull(ProtoUtils.toJson(wowCrawler.downloadPvPSeasonIndex())));
    }

}
