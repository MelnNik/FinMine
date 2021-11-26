package com.finmine.finmine;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.finmine.whatworks.strategy.WhatWorksStrategyTickers;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Finmine implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double performance;
    @OneToMany(mappedBy = "finmine", fetch = FetchType.LAZY)
    @JsonManagedReference
    @ApiModelProperty(notes = "List of tickers in the analytical strategy")
    List<FinmineTickers> finmineTickers;

    public Finmine(String name) {
        this.name = name;
    }
}
