package com.itwopqq.booting.system.service;

import com.itwopqq.booting.system.model.Person;

import java.util.List;

/**
* 通用 service 代码生成器
* @description
* @author business.generator
*/
public interface PersonService  {

    void addPersons(List<Person> persons);

    Object getFromCache(Long personId);
}




