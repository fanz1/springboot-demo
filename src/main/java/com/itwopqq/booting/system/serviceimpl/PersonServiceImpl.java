package com.itwopqq.booting.system.serviceimpl;

import com.itwopqq.booting.config.RedisConfig;
import com.itwopqq.booting.system.mapper.PersonMapper;
import com.itwopqq.booting.system.model.Person;
import com.itwopqq.booting.system.service.PersonService;
import com.itwopqq.booting.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 通用 serviceImpl 代码生成器
 *
 * @author business.generator
 * @description
 */
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonMapper personMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public void addPersons(List<Person> persons) {
        if (CollectionUtils.isEmpty(persons)) {
            return;
        }
        for (Person person : persons) {
            personMapper.insert(person);
            redisUtil.set(RedisConfig.REDIS_KEY_PERSON + person.getId(), person);
        }
    }

    @Override
    public Object getFromCache(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        String key = RedisConfig.REDIS_KEY_PERSON + id;
        return redisUtil.get(key);
    }
}




