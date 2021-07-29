package com.mobiquity.domains.clients;

import com.mobiquity.domains.builders.UserBuilder;
import com.mobiquity.domains.entity.User;
import com.mobiquity.domains.response.UsersResponse.UsersResponse;
import com.mobiquity.domains.services.UsersServices;
import org.apache.log4j.Logger;

public class UsersClient {

    private final UsersServices usersServices;
    private final Logger logger = Logger.getLogger(UsersClient.class);

    public UsersClient() {
        usersServices = new UsersServices();
    }

    public UsersResponse getUserWithUsername(String username) {
        logger.info("Getting a user with username = " + username);
        return usersServices.getUserWithUsername(username)[0];
    }

    public int getLatestUserId() {
        int length = usersServices.getAllUsers().length;
        logger.info("Getting latest user ID = " + length);
        return usersServices.getAllUsers()[length - 1].getId();
    }

    public UsersResponse[] getUserWithId(int userId) {
        logger.info("Getting a user with id = " + userId);
        return usersServices.getUserWithID(userId);
    }

    public int postUser() {
        User user = new UserBuilder().build();
        logger.info("Posting a user with email = " + user.getEmail());
        return usersServices.postUser(user);
    }

    public int deleteUserAndGetStatusCode(int userId) {
        logger.info("Delete a user with user ID = " + userId + " and see status code 200");
        return usersServices.deleteUser(userId).getStatusCode();
    }
}
