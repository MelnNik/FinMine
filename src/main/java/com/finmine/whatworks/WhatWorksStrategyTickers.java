package com.finmine.whatworks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WhatWorksStrategyTickers implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticker;
    private Double buyPrice;
    private Double currentPrice;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private WhatWorksStrategy whatWorksStrategy;

    public WhatWorksStrategyTickers(String ticker, Double buyPrice, Double currentPrice, WhatWorksStrategy whatWorksStrategy) {
        this.ticker = ticker;
        this.buyPrice = buyPrice;
        this.currentPrice = currentPrice;
        this.whatWorksStrategy = whatWorksStrategy;
    }
}
