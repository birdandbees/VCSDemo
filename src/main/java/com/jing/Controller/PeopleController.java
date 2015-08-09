package com.jing.Controller;

/**
 * Created by jingjing on 8/2/15.
 */

import com.jing.Model.Family;
import com.jing.Model.Person;
import com.jing.Service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PeopleController {

    @Autowired
    private PeopleService service;

    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public
    @ResponseBody
    Person getId(@RequestParam(value = "id", required = true) final String id) {

        return (service.getPerson(id));

    }

    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = {"Content-type=application/json"})
    public
    @ResponseBody
    Person add(@RequestBody Person person) {
        return (service.addPeople(person));


    }

    @RequestMapping(method = RequestMethod.POST, value = "/update", headers = {"Content-type=application/json"})
    public
    @ResponseBody
    Person updateId(@RequestBody Person person) {
        return (service.updatePerson(person));

    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete")
    public
    @ResponseBody
    Person removeId(@RequestParam(value = "id", required = true) final String id) {
        return (service.removePerson(id));


    }

    @RequestMapping(method = RequestMethod.POST, value = "/addFamily", headers = {"Content-type=application/json"})
    public
    @ResponseBody
    List<Person> add(@RequestBody Family family) {
        return (service.addFamily(family.getFamily()));


    }

}
