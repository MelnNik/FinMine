package com.finmine.finmine;

import org.springframework.data.repository.CrudRepository;

public interface FinmineRepository extends CrudRepository<Finmine, Long> {

    Finmine findByName(String name);

}
