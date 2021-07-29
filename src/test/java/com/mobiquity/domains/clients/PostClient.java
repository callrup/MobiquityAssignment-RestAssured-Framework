package com.mobiquity.domains.clients;

import com.mobiquity.domains.services.PostServices;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PostClient {

    private final PostServices postServices;
    private final Logger logger = Logger.getLogger(PostClient.class);

    public PostClient() {
        postServices = new PostServices();
    }

    public ArrayList<Integer> getPostIdsOfAUser(int userId) {
        ArrayList<Integer> posts = postServices.getAllPostsIdsOfAUser(userId);
        logger.info("Getting of a user's (whose ID is " + userId + ") all Posts and see the number of Posts is (" + posts.size() + ") greater than 0");
        return posts;
    }
}