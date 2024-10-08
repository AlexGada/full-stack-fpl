package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/fpl")
@CrossOrigin(origins = "http://localhost:3000")
public class FPLController {

    // Base URL for the official FPL API
    private static final String FPL_API_BASE_URL = "https://fantasy.premierleague.com/api/";

    @Autowired
    private RestTemplate restTemplate;

    // Endpoint to fetch general information (players, teams, etc.)
    @GetMapping("/general-info")
    public ResponseEntity<String> getGeneralInfo() {
        String url = FPL_API_BASE_URL + "bootstrap-static/";
        try {
            String response = restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the exception (you can use a logging framework like SLF4J)
            System.err.println("Error fetching general info: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching general info");
        }
    }

    // Endpoint to fetch specific player details by ID
    @GetMapping("/player/{playerId}")
    public ResponseEntity<String> getPlayerDetails(@PathVariable("playerId") int playerId) {
        String url = FPL_API_BASE_URL + "element-summary/" + playerId + "/";
        try {
            String response = restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error fetching player details: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching player details");
        }
    }

    // Endpoint to fetch user team details by user ID and gameweek/event ID
    @GetMapping("/user/{userId}/gameweek/{eventId}/team")
    public ResponseEntity<String> getUserTeam(@PathVariable("userId") int userId, @PathVariable("eventId") int eventId) {
        String url = FPL_API_BASE_URL + "entry/" + userId + "/event/" + eventId + "/picks/";
        try {
            String response = restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error fetching user team: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user team");
        }
    }

    // Endpoint to fetch fixtures
    @GetMapping("/fixtures")
    public ResponseEntity<String> getFixtures() {
        String url = FPL_API_BASE_URL + "fixtures/";
        try {
            String response = restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error fetching fixtures: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching fixtures");
        }
    }
}