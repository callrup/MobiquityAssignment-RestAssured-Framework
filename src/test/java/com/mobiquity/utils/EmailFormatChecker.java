package com.mobiquity.utils;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailFormatChecker {

    private static final String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static int getInvalidMailNumber(Set<String> emails) {
        AtomicInteger invalidMails = new AtomicInteger();
        Pattern pattern = Pattern.compile(regex);

        emails.forEach(mail -> {
            Matcher matcher = pattern.matcher(mail);
            if(!matcher.matches()){
                System.out.println(mail + " is invalid");
                invalidMails.getAndIncrement();
            }
        });

        return invalidMails.get();
    }
}