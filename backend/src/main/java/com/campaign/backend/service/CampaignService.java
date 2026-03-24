package com.campaign.backend.service;

import com.campaign.backend.dto.CampaignDto;
import com.campaign.backend.model.Campaign;
import com.campaign.backend.repository.CampaignRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final AccountService accountService;

    public List<CampaignDto> getAllCampaigns() {
        return campaignRepository.findAll().stream()
                .map(CampaignDto::fromEntity)
                .collect(Collectors.toList());
    }

    public CampaignDto getCampaignDtoById(Long id) {
        return CampaignDto.fromEntity(getCampaignEntityById(id));
    }

    private Campaign getCampaignEntityById(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign with id: " + id +  " not found"));
    }

    @Transactional
    public CampaignDto createCampaign(CampaignDto dto) {

        if (campaignRepository.existsByName(dto.name())) {
            throw new RuntimeException("Campaign with name: '" + dto.name() + "' already exists");
        }

        accountService.deductFunds(dto.campaignFund());

        Campaign campaign = CampaignDto.toEntity(dto);
        Campaign savedCampaign = campaignRepository.save(campaign);

        return CampaignDto.fromEntity(savedCampaign);
    }

    @Transactional
    public CampaignDto updateCampaign(Long id, CampaignDto details) {
        if (campaignRepository.existsByNameAndIdNot(details.name(), id)) {
            throw new RuntimeException("Other Campaign with name: '" + details.name() + "' already exists");
        }

        Campaign campaign = getCampaignEntityById(id);

        campaign.setName(details.name());
        campaign.setKeywords(details.keywords());
        campaign.setBidAmount(details.bidAmount());
        campaign.setStatus(details.status());
        campaign.setTown(details.town());
        campaign.setRadius(details.radius());

        Campaign updatedCampaign = campaignRepository.save(campaign);
        return CampaignDto.fromEntity(updatedCampaign);
    }

    @Transactional
    public void deleteCampaign(Long id) {
        Campaign campaign = getCampaignEntityById(id);

        accountService.addFunds(campaign.getCampaignFund());

        campaignRepository.delete(campaign);
    }
}