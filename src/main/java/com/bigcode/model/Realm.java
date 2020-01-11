package com.bigcode.model;

import java.util.HashMap;
import java.util.Map;

public class Realm {

    private HashMap<String, String> key;
    private String name;
    private int id;
    private String slug;

    public Realm(){

    }

    public Realm(HashMap<String, String> key, String name, int id, String slug) {
        this.key = key;
        this.name = name;
        this.id = id;
        this.slug = slug;
    }

    public Map<String, String> getKey() {
        return key;
    }

    public void setKey(HashMap<String, String> key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "Realm{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", slug='" + slug + '\'' +
                '}';
    }
}
