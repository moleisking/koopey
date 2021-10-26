package com.koopey.api.model.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.model.entity.Location;
import java.io.IOException;
import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GoogleParser {
    
    static public Location placeToLocation(Location location, String input){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info("Body: {}",input);

            JsonNode root = objectMapper.readTree(input);

            location.setLatitude(new BigDecimal(root.path("$.candidates[:1].geometry.location.lat").toString()));
            location.setLongitude(new BigDecimal(root.path("$.candidates[:1].geometry.location.lng").toString()));
            location.setPlace(root.path("$.candidates[:1].place_id").asText());
           

            log.info("Location: {}", location.toString());
            log.info("Status: {}", root.path("$.status").toString());
           

        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        return location;
    }

    static public Location geocodeToLocation(Location location, String input){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info("Body: {}",input);

            JsonNode root = objectMapper.readTree(input);

            location.setLatitude(new BigDecimal( root.path("$.results[:1].geometry.location.lat").toString()));
            location.setLongitude(new BigDecimal( root.path("$.results[:1].geometry.location.lng").toString()));
            location.setPlace(root.path("$.candidates[:1].place_id").asText());
           

            log.info("Location: {}", location.toString());
            log.info("Status: {}", root.path("$.status").toString());
           

        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        return location;
    }
}
