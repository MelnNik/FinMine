package com.finmine.whatworks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WhatWorksStrategy implements Serializable {

    // TODO: table for each strategy with its name, tickers and price of each ticker on buy and current

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // TODO: add performance column that aggregates tickers performance
    private Double performance;

    @OneToMany(mappedBy = "whatWorksStrategy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<WhatWorksStrategyTickers> whatWorksStrategyTickers;

    public WhatWorksStrategy(String name) {
        this.name = name;
    }
}

