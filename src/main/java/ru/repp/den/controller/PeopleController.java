package ru.repp.den.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.repp.den.dto.People;
import ru.repp.den.service.PeopleService;

import java.util.List;

@RestController
@RequestMapping("/request")
public class PeopleController {

    @Autowired
    PeopleService peopleService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String startProcessing(@RequestBody Integer monthNumber) {
        return peopleService.preparePeopleByMonth(monthNumber);
    }

    @RequestMapping(value = "/{requestId}", method = RequestMethod.GET)
    public List<People> getRequestResult(@PathVariable String requestId) {
        return peopleService.getRequestResult(requestId);
    }
}
