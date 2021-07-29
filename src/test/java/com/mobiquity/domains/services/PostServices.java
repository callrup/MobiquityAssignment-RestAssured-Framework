package com.mobiquity.domains.services;

import com.mobiquity.core.client.RestClient;
import java.util.ArrayList;

public class PostServices extends RestClient {

    private final String endpoint;

    public PostServices() {
        endpoint = "/posts";
    }

    public ArrayList<Integer> getAllPostsIdsOfAUser(int userId) {
        return getWithQueryParam(endpoint, "userId", userId).then().extract().path("id");
    }
}