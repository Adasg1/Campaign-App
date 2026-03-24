package com.campaign.backend.controller;

import com.campaign.backend.model.Campaign;
import com.campaign.backend.service.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @GetMapping
    public List<Campaign> getAll() {
        return campaignService.getAllCampaigns(); // Pobieramy przez serwis
    }

    @PostMapping
    public Campaign create(@Valid @RequestBody Campaign campaign) {
        return campaignService.createCampaign(campaign);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        campaignService.deleteCampaign(id); // Teraz używamy serwisu, więc środki wrócą na konto!
    }

    @PutMapping("/{id}")
    public Campaign update(@PathVariable Long id, @Valid @RequestBody Campaign campaign) {
        return campaignService.updateCampaign(id, campaign);
    }
}