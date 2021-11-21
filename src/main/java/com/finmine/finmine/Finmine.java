package com.finmine.finmine;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.finmine.whatworks.strategy.WhatWorksStrategyTickers;
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
    private Double performance;
    @OneToMany(mappedBy = "finmine", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<FinmineTickers> finmineTickers;
}
