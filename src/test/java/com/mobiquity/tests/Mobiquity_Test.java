package com.mobiquity.tests;

import com.mobiquity.domains.clients.CommentsClient;
import com.mobiquity.domains.clients.PostClient;
import com.mobiquity.domains.clients.UsersClient;
import com.mobiquity.domains.response.UsersResponse.UsersResponse;
import io.qameta.allure.Description;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Smoke")
@DisplayName("Mobiquity Test Cases")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(TestListener.class)
public class Mobiquity_Test {

    private final UsersClient usersClient;
    private final PostClient postClient;
    private final CommentsClient commentsClient;
    private final String name;
    private final Logger logger;


    public Mobiquity_Test() {
        usersClient = new UsersClient();
        postClient = new PostClient();
        commentsClient = new CommentsClient();
        logger = Logger.getLogger(Mobiquity_Test.class);
        name = "Delphine";
    }

    @Test
    @Tag("GET")
    @DisplayName("GET User with Username")
    @Description("GET User with Username = Delphine and see its existence")
    public void getUser() {
        assertNotNull(usersClient.getUserWithUsername(name));

    }

    @Test
    @Order(1)
    @Tag("POST")
    @DisplayName("POST A User")
    @Description("POST A new user and check whether a new id is generated")
    public void postUser() {
        logger.info("Posting a new user to see if it gets bigger ID than the lastest one");
        assertNotEquals(usersClient.getLatestUserId(), usersClient.postUser());
    }

    @Test
    @Tag("GET")
    @DisplayName("GET Post IDs of a User")
    @Description("GET Post IDs of Username Delphine")
    public void getPostIDs() {
        assertNotEquals(0,postClient.getPostIdsOfAUser(usersClient.getUserWithUsername(name).getId()).size());
    }

    @Test
    @Tag("GET")
    @DisplayName("GET Invalid Email Number")
    @Description("GET Emails from Delphine's Posts and check their format")
    public void checkEmailFormat() {
        logger.info("Getting a user with username and its ID and expecting there is no email in invalid format");
        assertEquals(0, commentsClient.getNumberOfInvalidEmailsFromPostComments(
                postClient.getPostIdsOfAUser(
                        usersClient.getUserWithUsername(name).getId())));
    }

    @Test
    @Order(1)
    @Tag("DELETE")
    @DisplayName("DELETE User")
    @Description("DELETE A User with ID = 5 and get Success Code")
    public void deleteUser() {
        assertEquals(200,usersClient.deleteUserAndGetStatusCode(5));
    }

    @Test
    @Order(2)
    @Tag("GET")
    @DisplayName("GET Deleted User")
    @Description("GET Deleted User, in the step above, and expect Status Code NOT to be 200")
    public void getDeletedUser() {
        logger.info("Trying to see if I can still get a user which is already deleted");
        assertEquals(0,usersClient.getUserWithId(5).length);
    }
}