package com.finmine.finmine;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FinmineTickers implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticker;
    private Double buyPrice;
    // TODO: Create service to AutoUpdatePrice
    private Double currentPrice;
    private Double Multiplier;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    @JsonBackReference
    private Finmine finmine;

    public FinmineTickers(String ticker, Double buyPrice, Double multiplier, Finmine finmine) {
        this.ticker = ticker;
        this.buyPrice = buyPrice;
        Multiplier = multiplier;
        this.finmine = finmine;
    }
}
