package com.example.jaimouillersabarbe.models;

import java.util.Map;

public class User {
    String firstName;

    public User(String first) {
        firstName = first;
    }

    public String getFirstName() {
        return firstName;
    }

    public static User fromHashMap(Map map){
        return new User((String) map.get("first"));
    }
}
