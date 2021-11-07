package com.finmine.whatworks;

import net.bytebuddy.TypeCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface WhatWorksStrategyTickersRepository extends CrudRepository<WhatWorksStrategyTickers, Long> {

    List<WhatWorksStrategyTickers> findByWhatWorksStrategy(WhatWorksStrategy whatWorksStrategy, Sort sort);

}
