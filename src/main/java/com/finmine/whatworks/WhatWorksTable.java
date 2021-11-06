package com.finmine.whatworks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WhatWorksTable {

    // TODO: Table for main view with all strategies' stats with strategy's name; historical performance from book and current performance
    // Send latest saved table to main view on request

    @SequenceGenerator(name = "table_sequance", sequenceName = "table_sequance", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_sequance")
    private Long id;


}
