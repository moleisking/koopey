package com.koopey.api.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.configuration.CustomProperties;
import com.koopey.api.model.entity.Location;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import ;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@EnableConfigurationProperties(CustomProperties.class)
@Slf4j
@Service
public class LocationService {

    @Autowired
    private CustomProperties customProperties;

    public Location findPlaceSearch(Location location) {

       log.info("GoogleApiKey:", customProperties.getGoogleApiKey());
       log.info("XXX:", customProperties.getXxx());

      /*  String addressUri = "";

        try {
            addressUri = URLEncoder.encode(location.getAddress(), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
        }

        final String uri = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json"; 

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("key", customProperties.getGoogleApiKey());
        parameters.put("input", addressUri);
        parameters.put("inputtype", "textquery");
        parameters.put("fields", "geometry,place_id");

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class, parameters);

        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode root = objectMapper.readTree(response.getBody());
            
            String slatitude = root.path("$.candidates[:1].geometry.location.lat").toString();
            log.info(slatitude);
            String slongitude = root.path("$.candidates[:1].geometry.location.lng").toString();
            log.info(slongitude);
            Long latitude = root.path("$.candidates[:1].geometry.location.lat").asLong();
            log.info(latitude.toString());
            Long longitude = root.path("$.candidates[:1].geometry.location.lng").asLong();
            log.info(longitude.toString());
            location.setLatitude(latitude);
       
        }catch (IOException ex) {
            log.error(ex.getMessage());
        }
  

        log.info(response.getBody());*/
        return location;
    }

    public Location findPlaceDetails(Location location) {

        String addressUri = "";

        try {
            addressUri = URLEncoder.encode(location.getAddress(), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
        }
        final String uri = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json"; 

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity(headers);

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("key", customProperties.getGoogleApiKey());
        parameters.put("input", addressUri);
        parameters.put("inputtype", "textquery");
        parameters.put("fields", "geometry,place_id");

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class, parameters);


        log.info(response.getBody());
        return location;
    }
}
