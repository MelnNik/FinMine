package com.finmine.whatworks.strategy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhatWorksStrategyRepository extends CrudRepository<WhatWorksStrategy, Long> {
    WhatWorksStrategy findByName(String name);
}
