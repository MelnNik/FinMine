package com.finmine.whatworks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhatWorksTableRepository extends JpaRepository<WhatWorksTable, Long> {
}
