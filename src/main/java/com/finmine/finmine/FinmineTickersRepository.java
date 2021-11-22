package com.finmine.finmine;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinmineTickersRepository extends CrudRepository<FinmineTickers, Long> {
}
