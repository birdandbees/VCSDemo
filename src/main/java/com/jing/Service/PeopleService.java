package com.jing.Service;

/**
 * Created by jingjing on 8/2/15.
 */

import com.jing.Model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleService {
    @Autowired
    private RedisTemplate<String, String> template;

    private final RedisAtomicLong userIdCounter;

    @Inject
    public PeopleService(StringRedisTemplate template) {
        this.template = template;


        userIdCounter = new RedisAtomicLong("uid", template.getConnectionFactory());
    }

    public Person addPeople(final Person person) {
        final String uid = String.valueOf(userIdCounter.incrementAndGet());

        List<Object> results = template.execute(new SessionCallback<List<Object>>() {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().set(KeyHelper.getUser(uid), person.getName());
                operations.opsForList().leftPushAll(KeyHelper.getAddress(uid), person.getAddresses().toArray(new String[person.getAddresses().size()]));
                return operations.exec();
            }
        });
        person.setUid(uid);
        return person;

    }

    public Person getPerson(String key) {
        String existingRecord = (String) template.opsForValue().get(KeyHelper.getUser(key));
        if (existingRecord == null) {
            return null;
        }
        Person person = new Person();
        person.setName(existingRecord);
        List<String> addresses = new ArrayList<String>();
        addresses = template.boundListOps(KeyHelper.getAddress(key)).range(0, template.boundListOps(KeyHelper.getAddress(key)).size());
        person.setAddresses(addresses);
        person.setUid(key);
        return person;

    }

    ;


    public Person updatePerson(final Person person) {
        final String key = person.getUid();
        String existingRecord = (String) template.opsForValue().get(KeyHelper.getUser(key));
        if (existingRecord == null) {
            return null;
        }
        System.out.println("key found, updating");
        List<Object> results = template.execute(new SessionCallback<List<Object>>() {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().set(KeyHelper.getUser(key), person.getName());
                operations.delete(KeyHelper.getAddress(key));
                operations.opsForList().leftPushAll(KeyHelper.getAddress(key), person.getAddresses().toArray(new String[person.getAddresses().size()]));
                return operations.exec();
            }
        });

        return person;

    }

    public Person removePerson(final String key) {
        String existingRecord = (String) template.opsForValue().get(KeyHelper.getUser(key));
        if (existingRecord == null) {
            return null;
        }

        List<Object> results = template.execute(new SessionCallback<List<Object>>() {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.delete(KeyHelper.getUser(key));
                operations.delete(KeyHelper.getAddress(key));
                return operations.exec();
            }
        });


        Person person = new Person();
        person.setUid(key);
        return person;
    }

    public List<Person> addFamily(final List<Person> family) {
        List<Person> result = new ArrayList<>();
        for (Person person : family) {
            result.add(addPeople(person));
        }
        return result;

    }

}
