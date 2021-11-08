package com.finmine.utilities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@Getter
@Setter
@NoArgsConstructor
public class GetStockPrice {
    // TODO: Function to return price of a ticker https://github.com/sstrickx/yahoofinance-api
    private Double price;

    @SneakyThrows
    public static Double getCurrentPrice(String ticker) {
        Stock stock = YahooFinance.get(ticker);
        return stock.getQuote().getPrice().doubleValue();

    }
}
