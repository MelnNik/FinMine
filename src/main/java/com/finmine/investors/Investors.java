package com.finmine.investors;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.finmine.whatworks.strategy.WhatWorksStrategyTickers;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Investors implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // TODO: add performance column that aggregates tickers performance(new package utils as price updater will be used in other parts as well)
    private Double performance;

    @OneToMany(mappedBy = "investors", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<InvestorsTickers> investorsTickers;
}
