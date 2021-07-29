package com.mobiquity.domains.clients;

import com.mobiquity.domains.services.CommentsServices;
import java.util.ArrayList;

public class CommentsClient {

    private final CommentsServices commentsServices;

    public CommentsClient() {
        commentsServices = new CommentsServices();
    }

    public int getNumberOfInvalidEmailsFromPostComments(ArrayList<Integer> postIds) {
        return commentsServices.getInvalidMailCount(postIds);
    }
}