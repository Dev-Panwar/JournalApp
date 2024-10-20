package com.devpanwar.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class WeatherResponse{
//    we shortened the property for the response we want
// as we are making changes from the json names in variable as we are not using snake case...so we have to tell the original name of json property

    private Current current;

    @Setter
    @Getter
    public class Current{
        @JsonProperty("observation_time")
        private String observationTime;

        private int temperature;

        @JsonProperty("weather_code")
        private int weatherCode;

        @JsonProperty("weather_icons")
        private ArrayList<String> weatherIcons;

        @JsonProperty("weather_descriptions")
        private ArrayList<String> weatherDesc;

        @JsonProperty("wind_dir")
        private String windDirection;

        private int pressure;
        private int humidity;
        private int feelslike;
        private int uv_index;
    }

}



