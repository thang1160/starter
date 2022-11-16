package com.example.starter;

public enum Path {
    LOGIN("/login"),
    LOGOUT("/logout"),
    PROJECT("/project"),
    TEST_CASE("/test-case"),
    MILESTONE("/milestone");

    private final String stringPath;

    Path(final String path) {
        this.stringPath = path;
    }

    @Override
    public String toString() {
        return stringPath;
    }
}
