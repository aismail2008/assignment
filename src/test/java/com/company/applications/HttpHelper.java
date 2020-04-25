package com.company.applications;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HttpHelper {

    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .followRedirects(false)
            .followSslRedirects(false)
            .build();

    public static Response doGet(String url, String... headers) throws IOException {
        Request.Builder request = new Request.Builder()
                .get()
                .url(url);

        for (int i = 0; i < headers.length - 1; i = i + 2) {
            request.header(headers[i], headers[i + 1]);
        }

        return client.newCall(request.build()).execute();
    }

    public static Response doDelete(String url, String... headers) throws IOException {
        Request.Builder request = new Request.Builder()
                .delete()
                .url(url);

        for (int i = 0; i < headers.length - 1; i = i + 2) {
            request.header(headers[i], headers[i + 1]);
        }

        return client.newCall(request.build()).execute();
    }
}