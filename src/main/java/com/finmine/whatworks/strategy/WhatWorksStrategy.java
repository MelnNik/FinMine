package com.finmine.whatworks.strategy;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WhatWorksStrategy implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // TODO: add performance column that aggregates tickers performance(new package utils as price updater will be used in other parts as well)
    private Double performance;

    @OneToMany(mappedBy = "whatWorksStrategy", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<WhatWorksStrategyTickers> whatWorksStrategyTickers;

    // TODO: ManyToOne with WhatWorks

    public WhatWorksStrategy(String name) {
        this.name = name;
    }
}

