package com.campaign.backend.service;

import com.campaign.backend.model.EmeraldAccount;
import com.campaign.backend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public BigDecimal getBalance() {
        return accountRepository.findById(1L)
                .map(EmeraldAccount::getBalance)
                .orElse(BigDecimal.ZERO);
    }

    public void deductFunds(BigDecimal amount) {
        EmeraldAccount account = accountRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Not enough funds on Emerald Account");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    public void addFunds(BigDecimal amount) {
        EmeraldAccount account = accountRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }
}