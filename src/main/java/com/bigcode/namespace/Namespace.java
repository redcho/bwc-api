package com.bigcode.namespace;

public enum Namespace {
    Static("static-eu"),
    Dynamic("dynamic-eu"),
    Profile("profile-eu");

    private String url;

    Namespace(String envUrl) {
        this.url = envUrl;
    }

    public String getNS() {
        return url;
    }
}
