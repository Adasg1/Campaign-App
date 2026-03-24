package com.campaign.backend.controller;

import com.campaign.backend.dto.CampaignDto;
import com.campaign.backend.service.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @GetMapping("/{id}")
    public CampaignDto getById(@PathVariable Long id) {
        return campaignService.getCampaignDtoById(id);
    }

    @GetMapping
    public List<CampaignDto> getAll() {
        return campaignService.getAllCampaigns();
    }

    @PostMapping
    public CampaignDto create(@Valid @RequestBody CampaignDto campaignDto) {
        return campaignService.createCampaign(campaignDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
    }

    @PutMapping("/{id}")
    public CampaignDto update(@PathVariable Long id, @Valid @RequestBody CampaignDto campaignDto) {
        return campaignService.updateCampaign(id, campaignDto);
    }
}