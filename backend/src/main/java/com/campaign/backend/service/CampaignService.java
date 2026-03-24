package com.campaign.backend.service;

import com.campaign.backend.model.Campaign;
import com.campaign.backend.repository.CampaignRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final AccountService accountService; // Wstrzykujemy AccountService!

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public Campaign getCampaignById(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono kampanii o id: " + id));
    }

    @Transactional
    public Campaign createCampaign(Campaign campaign) {
        // Zlecamy pobranie środków z konta - metoda rzuci wyjątek, jeśli brakuje kasy
        accountService.deductFunds(campaign.getCampaignFund());

        return campaignRepository.save(campaign);
    }

    @Transactional
    public Campaign updateCampaign(Long id, Campaign details) {
        Campaign campaign = getCampaignById(id);

        campaign.setName(details.getName());
        campaign.setKeywords(details.getKeywords());
        campaign.setBidAmount(details.getBidAmount());
        campaign.setStatus(details.getStatus());
        campaign.setTown(details.getTown());
        campaign.setRadius(details.getRadius());

        return campaignRepository.save(campaign);
    }

    @Transactional
    public void deleteCampaign(Long id) {
        Campaign campaign = getCampaignById(id);

        // Zwracamy środki na konto przy usuwaniu
        accountService.addFunds(campaign.getCampaignFund());

        campaignRepository.delete(campaign);
    }
}