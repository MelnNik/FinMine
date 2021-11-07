package com.finmine.whatworks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WhatWorks {

    // TODO: Table for main view with all strategies' stats with strategy's name; historical performance from book and current performance
    // Send latest saved table to main view on request

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // TODO: OneToMany with what works strategy


}
