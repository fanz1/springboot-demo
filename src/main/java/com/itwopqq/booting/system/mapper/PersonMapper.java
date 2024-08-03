package com.itwopqq.booting.system.mapper;

import com.itwopqq.booting.system.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonMapper {

    Integer insert(Person person);
}