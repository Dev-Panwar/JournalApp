package com.devpanwar.journalApp.service;


import com.devpanwar.journalApp.api.response.WeatherResponse;
import com.devpanwar.journalApp.cache.AppCache;
import com.devpanwar.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

//    for making the http request
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
//        we have stored our endpoint in the DB....and we are retrieving it using appCache..getting apiUrl and updating it with suitable params
        WeatherResponse CachedweatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (CachedweatherResponse!=null){
//            getting from redis
            return CachedweatherResponse;
        }else {
            String finalApi = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
//            storing in redis
            if (body!=null){
                redisService.set("weather_of_"+city,body,300l);
            }
            return body;
        }
    }
}
