package com.example.easyexchanger.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants  {

    public static String API_URL;
    public static final String API_VERSION = "/v6/";
    public static String API_KEY;
    public static final String API_STATUS = "/latest/";

    @Value("${easy-exchanger.api-url}")
    public void setApiUrl(String apiUrl) {
        Constants.API_URL = apiUrl;
    }

    @Value("${easy-exchanger.api-key}")
    public void setApiKey(String apiKey) {
        Constants.API_KEY = apiKey;
    }

}
