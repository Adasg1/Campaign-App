package com.campaign.backend.service;

import com.campaign.backend.model.EmeraldAccount;
import com.campaign.backend.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private EmeraldAccount testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new EmeraldAccount();
        testAccount.setId(1L);
        testAccount.setBalance(new BigDecimal("500.0"));
    }

    @Test
    void shouldDeductFundsSuccessfully() {
        // GIVEN
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        // WHEN
        accountService.deductFunds(new BigDecimal("100.0"));

        // THEN
        assertEquals(new BigDecimal("400.0"), testAccount.getBalance());

        verify(accountRepository, times(1)).save(testAccount);
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsTooLow() {
        // GIVEN
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.deductFunds(new BigDecimal("1000.0"));
        });

        assertEquals("Not enough funds on Emerald Account", exception.getMessage());

        assertEquals(new BigDecimal("500.0"), testAccount.getBalance());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void shouldAddFundsSuccessfully() {
        // 1. GIVEN
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        // 2. WHEN
        accountService.addFunds(new BigDecimal("200.0"));

        // 3. THEN
        assertEquals(new BigDecimal("700.0"), testAccount.getBalance());
        verify(accountRepository, times(1)).save(testAccount);
    }
}