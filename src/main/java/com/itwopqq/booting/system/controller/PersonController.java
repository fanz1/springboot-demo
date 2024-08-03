package com.itwopqq.booting.system.controller;

import com.itwopqq.booting.config.AjaxResult;
import com.itwopqq.booting.system.model.Person;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.itwopqq.booting.system.service.PersonService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* 通用 Controller 代码生成器
* @description
* @author business.generator
*/
@Slf4j
@RestController
public class PersonController {

    @Autowired
    private PersonService  personService;


    /***
     * @desc 添加用户，为了方便使用，设置成GET模式
     * @param
     * @return com.itwopqq.booting.config.AjaxResult
     */
    @GetMapping("/add/person/test")
    public AjaxResult addPerson(){

        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Person person =  new Person();
            person.setAge(RandomUtils.nextInt(1, 99));
            person.setName(RandomString.make(3));
            personList.add(person);
        }
        personService.addPersons(personList);
        return AjaxResult.success("添加成功", personList);
    }

    @GetMapping("/info/get/{id}")
    public AjaxResult getPerson(@PathVariable(value = "id") Long id) {


        Object o = personService.getFromCache(id);
        return AjaxResult.success("查询成功", o);
    }
}
