package com.campaign.backend.repository;

import com.campaign.backend.model.EmeraldAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<EmeraldAccount, Long> {}