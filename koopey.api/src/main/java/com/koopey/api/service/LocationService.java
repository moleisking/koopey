package com.koopey.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.configuration.CustomProperties;
import com.koopey.api.model.entity.Location;
import com.koopey.api.repository.LocationRepository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocationService {

    @Autowired
    private CustomProperties customProperties;

    @Autowired
    private LocationRepository locationRepository;

    @PostConstruct
    private void init() {
        log.info("GOOGLE_API_KEY: {}", System.getenv("GOOGLE_API_KEY"));
        log.info("GoogleApiKey: {}", customProperties.getGoogleApiKey());
    }

    public Location findPlaceSearch(Location location) {
        log.info("findPlaceSearch with key {}", System.getenv("GOOGLE_API_KEY"));

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?")
                .queryParam("fields", "geometry,place_id").queryParam("input", location.getAddress())
                .queryParam("inputtype", "textquery").queryParam("key", customProperties.getGoogleApiKey());

        log.info("UrlBuilder: {}", uriBuilder.toUriString());

        HttpEntity<String> response = new RestTemplate().exchange(uriBuilder.toUriString(), HttpMethod.GET,
                new HttpEntity<String>(headers), String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
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

        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        log.info(response.getBody());

        return location;
    }

    public Location findPlaceDetails(Location location) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?")
                .queryParam("fields", "geometry,place_id").queryParam("input", location.getAddress())
                .queryParam("inputtype", "textquery").queryParam("key", customProperties.getGoogleApiKey());

        log.info("findPlaceDetails with url {}", uriBuilder.toUriString());

        HttpEntity<String> response = new RestTemplate().exchange(uriBuilder.toUriString(), HttpMethod.GET,
                new HttpEntity<String>(headers), String.class);

        log.info(response.getBody());
        return location;
    }
}
