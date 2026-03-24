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

    @NotBlank(message = "Nazwa kampanii jest obowiązkowa")
    private String name;

    @NotEmpty(message = "Lista słów kluczowych nie może być pusta")
    @ElementCollection
    private List<String> keywords;

    @NotNull(message = "Bid amount jest obowiązkowy")
    @DecimalMin(value = "10.0", inclusive = true, message = "Minimalny bid amount to 10.0") // Przykładowa kwota minimalna, możesz ją zmienić
    private BigDecimal bidAmount;

    @NotNull(message = "Fundusz kampanii jest obowiązkowy")
    @DecimalMin(value = "0.1", inclusive = true, message = "Fundusz kampanii musi być większy niż 0")
    private BigDecimal campaignFund;

    @NotNull(message = "Status kampanii jest obowiązkowy")
    private Boolean status; // Używam obiektu Boolean

    @NotBlank(message = "Miasto jest obowiązkowe")
    private String town;

    @NotNull(message = "Promień jest obowiązkowy")
    @Min(value = 1, message = "Promień musi wynosić przynajmniej 1 km")
    private Integer radius; // Używam obiektu Integer
}