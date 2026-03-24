package com.campaign.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dictionaries")
@CrossOrigin(origins = "http://localhost:5173")
public class DictionaryController {

    @GetMapping("/towns")
    public List<String> getTowns() {
        return List.of("Warszawa", "Kraków", "Wrocław", "Poznań", "Gdańsk", "Łódź", "Szczecin");
    }

    @GetMapping("/keywords")
    public List<String> getKeywords() {
        return List.of("smartphones", "shoes", "electronics", "fashion", "home", "garden", "toys", "automotive", "books", "health");
    }
}