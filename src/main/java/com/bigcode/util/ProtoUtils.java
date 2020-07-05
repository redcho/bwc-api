package com.bigcode.util;

import com.bigcode.crawler.WowCrawler;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProtoUtils {
    private static Logger log = LogManager.getLogger(WowCrawler.class);

    private ProtoUtils() { }

    public static String toJson(Message message) {
        try {
            return JsonFormat.printer().print(message);
        }catch (InvalidProtocolBufferException ex){
            log.error(log);
            return null;
        }
    }

    public static <T extends Message> T fromJson(String json, Message.Builder builder){
        try {
            JsonFormat.parser().ignoringUnknownFields().merge(json, builder);
            return (T) builder.build();
        } catch (InvalidProtocolBufferException e) {
            log.error(log);
            return null;
        }
    }
}
