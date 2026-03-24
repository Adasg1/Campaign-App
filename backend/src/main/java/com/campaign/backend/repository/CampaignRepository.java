package com.campaign.backend.repository;

import com.campaign.backend.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}