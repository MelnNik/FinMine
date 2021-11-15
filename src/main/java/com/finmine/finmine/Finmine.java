package com.finmine.finmine;

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
public class Finmine implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // TODO: add performance column that aggregates tickers performance(new package utils as price updater will be used in other parts as well)
    private Double performance;

    @OneToMany(mappedBy = "finmine", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<FinmineTickers> finmineTickers;

    public Finmine(String investor) {
    }
}
