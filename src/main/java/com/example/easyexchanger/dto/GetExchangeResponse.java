package com.example.easyexchanger.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public record GetExchangeResponse(
        String result,

        //String documentation,

        //@JsonProperty("terms_of_use")
        //String termsOfUse,

        //@JsonProperty("time_last_update_unix")
        //String timeLastUpdateUnix,

        @JsonProperty("time_last_update_utc")
        String timeLastUpdateUtc,

        @JsonProperty("time_next_update_utc")
        String timeNextUpdateUtc,

        //@JsonProperty("time_next_update_unix")
        //String timeNextUpdateUnix,

        @JsonProperty("base_code")
        String baseCode,

        @JsonProperty("conversion_rates")
        Map<String,Double> conversionRates

) {


}


