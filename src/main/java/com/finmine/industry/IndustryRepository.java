package com.finmine.industry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends CrudRepository<Industry, Long> {
}
