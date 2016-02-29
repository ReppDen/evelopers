package ru.repp.den.service;

import ru.repp.den.dto.People;

import java.util.List;

public interface PeopleService {

    /**
     * starts the processing of available people in background. Return ID of the request
     * @param monthNumber month number
     * @return request ID
     */
    String preparePeopleByMonth(Integer monthNumber);

    /**
     * get the result of processing by requiest ID
     * @param requestId id of the rquest
     * @return list of people, matched the request
     */
    List<People> getRequestResult(String  requestId);
}
