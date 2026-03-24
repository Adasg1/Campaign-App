package com.campaign.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotEmpty
    @ElementCollection
    private List<String> keywords;

    @NotNull
    @DecimalMin(value = "10.0", inclusive = true)
    private BigDecimal bidAmount;

    @NotNull
    @DecimalMin(value = "0.1", inclusive = true)
    private BigDecimal campaignFund;

    @NotNull
    private Boolean status;

    @NotBlank
    private String town;

    @NotNull
    @Min(value = 1)
    private Integer radius;
}