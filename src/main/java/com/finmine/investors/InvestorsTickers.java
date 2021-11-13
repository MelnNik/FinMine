package com.finmine.investors;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.finmine.whatworks.strategy.WhatWorksStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class InvestorsTickers implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticker;
    private Double buyPrice;
    // TODO: Create service to AutoUpdatePrice
    private Double currentPrice;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    @JsonBackReference
    private Investors investors;
}
