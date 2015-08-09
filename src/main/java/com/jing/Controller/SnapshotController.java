package com.jing.Controller;

import com.jing.Model.ChangeSet;
import com.jing.Model.DailySnapshot;
import com.jing.Model.Person;
import com.jing.Service.ControllerService;
import com.jing.Service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jingjing on 8/7/15.
 */
@Controller
public class SnapshotController {
    @Autowired
    private ControllerService service;

    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = {"Content-type=application/json"})
    public
    @ResponseBody
    ChangeSet add(@RequestBody DailySnapshot dailySnapshot) {
        return service.addDailySnapshot(dailySnapshot);


    }

    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public
    @ResponseBody
    DailySnapshot getPT(@RequestParam(value = "date", required = true) final String date,
                 @RequestParam(value="acct", required = true) final String account) {

        return (service.getPerson(id));

    }

    @RequestMapping(method = RequestMethod.GET, value = "/getLatest")
    public
    @ResponseBody
    DailySnapshot getBT(@RequestParam(value = "acct", required = true) final String account) {

        return (service.getLatest(account));

    }

    @RequestMapping(method = RequestMethod.GET, value = "/getDelta")
    public
    @ResponseBody
    List<ChangeSet> getDelta(@RequestParam(value = "date", required = true) final String date,
                        @RequestParam(value="acct", required = true) final String account) {

        return (service.getPerson(id));

    }
}
