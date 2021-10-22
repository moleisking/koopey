package com.koopey.api.service;

import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.parser.GoogleParser;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class GoogleService {
    
    @Autowired
    private CustomProperties customProperties;    

    @PostConstruct
    private void init() {
        log.info("GOOGLE_API_KEY: {}", System.getenv("GOOGLE_API_KEY"));
        log.info("GoogleApiKey: {}", customProperties.getGoogleApiKey());
    }

    public Location findGeocode(Location location) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json?")
                .queryParam("address", location.getDescription().replace(" ", "+"))
                .queryParam("key", customProperties.getGoogleApiKey());

        log.info("findGeocode with url {}", uriBuilder.toUriString());

        HttpEntity<String> response = new RestTemplate().exchange(uriBuilder.toUriString(), HttpMethod.GET,
                new HttpEntity<String>(headers), String.class);

        return GoogleParser.geocodeToLocation(location, response.getBody());
    }

    public Location findPlaceDetails(Location location) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl("https://maps.googleapis.com/maps/api/place/details/json?")
                .queryParam("fields", "geometry,place_id").queryParam("input", location.getDescription())
                .queryParam("place_id", location.getPlace()).queryParam("key", customProperties.getGoogleApiKey());

        log.info("findPlaceDetails with url {}", uriBuilder.toUriString());

        HttpEntity<String> response = new RestTemplate().exchange(uriBuilder.toUriString(), HttpMethod.GET,
                new HttpEntity<String>(headers), String.class);

        return GoogleParser.placeToLocation(location, response.getBody());
    }

    public Location findPlaceSearch(Location location) {
        log.info("findPlaceSearch with key {}", System.getenv("GOOGLE_API_KEY"));

        HttpHeaders headers = new HttpHeaders();
       // headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?")
                .queryParam("fields", "geometry,place_id").queryParam("input", location.getDescription())
                .queryParam("inputtype", "textquery").queryParam("key", customProperties.getGoogleApiKey());

        log.info("UrlBuilder: {}", uriBuilder.toUriString());

        HttpEntity<String> response = new RestTemplate().exchange(uriBuilder.toUriString(), HttpMethod.GET,
                new HttpEntity<String>(headers), String.class);

        return GoogleParser.placeToLocation(location, response.getBody());
    }   
   
}
