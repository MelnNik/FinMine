package com.finmine.whatworks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WhatWorksStrategy {

    // TODO: table for each strategy with its name, tickers and price of each ticker on buy and current

    @SequenceGenerator(name = "strategy_sequance", sequenceName = "strategy_sequance", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "strategy_sequance")
    private Long id;


}
