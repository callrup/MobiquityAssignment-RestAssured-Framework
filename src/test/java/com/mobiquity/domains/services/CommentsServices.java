package com.mobiquity.domains.services;

import com.mobiquity.core.client.RestClient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static com.mobiquity.utils.EmailFormatChecker.getInvalidMailNumber;

public class CommentsServices extends RestClient {

    private final String endpoint;

    public CommentsServices() {
        endpoint = "/comments";
    }

    public ArrayList<ArrayList<String>> getEmailsFromComments(ArrayList<Integer> postIds){
        ArrayList<ArrayList<String>> mails = new ArrayList<>();
        postIds.forEach(postId -> mails.add(getWithQueryParam(endpoint, "postId", postId).then().extract().path("email")));
        return mails;
    }

    public int getInvalidMailCount(ArrayList<Integer> postIds){
        Set<String> distinctMails = new HashSet<>();
        getEmailsFromComments(postIds).forEach(distinctMails::addAll);
        return getInvalidMailNumber(distinctMails);
    }
}