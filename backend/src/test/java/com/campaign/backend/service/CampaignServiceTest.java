package com.campaign.backend.service;

import com.campaign.backend.dto.CampaignDto;
import com.campaign.backend.model.Campaign;
import com.campaign.backend.repository.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private CampaignService campaignService;

    @Test
    void shouldCreateCampaignSuccessfullyAndDeductFunds() {
        // GIVEN
        CampaignDto inputDto = new CampaignDto(
                null, "New Campaign", List.of("tag1"),
                new BigDecimal("15.0"), new BigDecimal("100.0"),
                true, "Warszawa", 10
        );

        Campaign savedEntity = new Campaign();
        savedEntity.setId(1L);
        savedEntity.setName("New Campaign");

        when(campaignRepository.existsByName("New Campaign")).thenReturn(false);
        when(campaignRepository.save(any(Campaign.class))).thenReturn(savedEntity);

        // WHEN
        CampaignDto result = campaignService.createCampaign(inputDto);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.id());

        verify(accountService, times(1)).deductFunds(new BigDecimal("100.0"));
        verify(campaignRepository, times(1)).save(any(Campaign.class));
    }

    @Test
    void shouldThrowExceptionWhenCampaignNameAlreadyExists() {
        // GIVEN
        CampaignDto inputDto = new CampaignDto(
                null, "Duplicate", List.of("tag1"),
                new BigDecimal("15.0"), new BigDecimal("100.0"),
                true, "Warszawa", 10
        );

        when(campaignRepository.existsByName("Duplicate")).thenReturn(true);

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            campaignService.createCampaign(inputDto);
        });

        assertEquals("Campaign with name: 'Duplicate' already exists", exception.getMessage());

        verify(accountService, never()).deductFunds(any());
        verify(campaignRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenInsufficientFunds() {
        // GIVEN
        CampaignDto inputDto = new CampaignDto(
                null, "Campaign", List.of("tag1"),
                new BigDecimal("15.0"), new BigDecimal("10000.0"),
                true, "Warszawa", 10
        );

        when(campaignRepository.existsByName("Campaign")).thenReturn(false);

        doThrow(new RuntimeException("Not enough funds on Emerald Account"))
                .when(accountService).deductFunds(new BigDecimal("10000.0"));

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            campaignService.createCampaign(inputDto);
        });

        assertEquals("Not enough funds on Emerald Account", exception.getMessage());

        verify(campaignRepository, never()).save(any(Campaign.class));
    }
}