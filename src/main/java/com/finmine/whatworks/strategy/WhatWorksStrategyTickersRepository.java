package com.finmine.whatworks.strategy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface WhatWorksStrategyTickersRepository extends CrudRepository<WhatWorksStrategyTickers, Long> {

    List<WhatWorksStrategyTickers> findByWhatWorksStrategy(WhatWorksStrategy whatWorksStrategy);

}
