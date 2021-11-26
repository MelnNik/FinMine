package com.finmine.whatworks.strategy;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class WhatWorksStrategy implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double performance;

    @OneToMany(mappedBy = "whatWorksStrategy", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<WhatWorksStrategyTickers> whatWorksStrategyTickers;

    public WhatWorksStrategy(String name) {
        this.name = name;
    }
}

