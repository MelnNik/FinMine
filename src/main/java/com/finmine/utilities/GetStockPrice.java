package com.finmine.utilities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetStockPrice {
    // TODO: Function to return price of a ticker
    private Double price;

    public Double getCurrentPrice(String ticker) {
        return price;
    }
}
