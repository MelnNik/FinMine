package com.finmine.whatworks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhatWorksStrategyRepository extends JpaRepository<WhatWorksStrategy, Long> {
}
