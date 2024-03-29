package com.example.campusee;

import java.util.HashMap;
import java.util.Map;

public class Publisher {
    public String name;
    public String email;
    public String password;
    public String building;

    Publisher(){

    }

    Publisher(String name, String email, String password, String building) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.building = building;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("email", email);
        result.put("password", password);
        result.put("building", building);
        return result;
    }
}
