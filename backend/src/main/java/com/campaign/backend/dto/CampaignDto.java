package com.campaign.backend.dto;

import com.campaign.backend.model.Campaign;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public record CampaignDto(
        Long id,
        @NotBlank String name,
        @NotEmpty List<String> keywords,
        @NotNull @DecimalMin("10.0") BigDecimal bidAmount,
        @NotNull @DecimalMin("0.1") BigDecimal campaignFund,
        @NotNull Boolean status,
        @NotBlank String town,
        @NotNull @Min(1) Integer radius
) {
    public static CampaignDto fromEntity(Campaign campaign) {
        return new CampaignDto(
                campaign.getId(),
                campaign.getName(),
                campaign.getKeywords(),
                campaign.getBidAmount(),
                campaign.getCampaignFund(),
                campaign.getStatus(),
                campaign.getTown(),
                campaign.getRadius()
        );
    }

    public static Campaign toEntity(CampaignDto dto) {
        Campaign campaign = new Campaign();
        campaign.setName(dto.name());
        campaign.setKeywords(dto.keywords());
        campaign.setBidAmount(dto.bidAmount());
        campaign.setCampaignFund(dto.campaignFund());
        campaign.setStatus(dto.status());
        campaign.setTown(dto.town());
        campaign.setRadius(dto.radius());
        return campaign;
    }
}