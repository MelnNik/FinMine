package com.finmine.finmine;

import org.springframework.data.repository.CrudRepository;

public interface FinmineTickersRepository extends CrudRepository<FinmineTickers, Long> {
    FinmineTickers findInvestorsTickersByTicker(String ticker);
}
