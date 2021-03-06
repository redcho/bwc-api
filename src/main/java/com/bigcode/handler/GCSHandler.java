package com.bigcode.handler;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

import static java.nio.charset.StandardCharsets.UTF_8;


public final class GCSHandler {

    private static final int DEFAULT_CHUNK_SIZE = 64 * 1024;

    private static Storage storage;

    static {
        storage = StorageOptions.getDefaultInstance().getService();
    }

    private GCSHandler(){

    }

    public static void uploadString(String bucketName, String fileName, String jsonData){
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();

        Blob blob = storage.create(blobInfo, jsonData.getBytes(UTF_8));
    }

    public static String downloadString(String bucketName, String fileName){
        Blob blob = storage.get(BlobId.of(bucketName, fileName));

        try(ReadChannel reader = blob.reader();
            ByteArrayOutputStream baos = new ByteArrayOutputStream()){

            WritableByteChannel channelToOutputStream = Channels.newChannel(baos);
            ByteBuffer bytes = ByteBuffer.allocate(DEFAULT_CHUNK_SIZE);
            StringBuilder sb = new StringBuilder();
            while(reader.read(bytes) > 0){
                bytes.flip();
                channelToOutputStream.write(bytes);
                bytes.clear();
            }

            return new String(baos.toByteArray(), UTF_8);
        }catch(IOException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
