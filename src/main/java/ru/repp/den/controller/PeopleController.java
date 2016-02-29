package ru.repp.den.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.repp.den.dto.People;
import ru.repp.den.service.PeopleService;

import java.util.List;

@RestController
@RequestMapping("/request")
public class PeopleController {
    public static final Logger LOGGER = LoggerFactory.getLogger(PeopleController.class);

    @Autowired
    PeopleService peopleService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String startProcessing(@RequestBody Integer monthNumber) {
        LOGGER.debug("Enter REST endpoint /request/ with method POST. Parameter is {}", monthNumber);
        return peopleService.preparePeopleByMonth(monthNumber);
    }

    @RequestMapping(value = "/{requestId}", method = RequestMethod.GET)
    public List<People> getRequestResult(@PathVariable String requestId) {
        LOGGER.debug("Enter REST endpoint /request/{requestId} with method GET. Parameter is {}", requestId);
        return peopleService.getRequestResult(requestId);
    }
}
