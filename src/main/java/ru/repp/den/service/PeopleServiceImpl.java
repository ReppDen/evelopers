package ru.repp.den.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.repp.den.FutureRegistry;
import ru.repp.den.dto.People;
import ru.repp.den.exception.BadRequestException;
import ru.repp.den.exception.StillProcessingException;
import ru.repp.den.worker.PeopleWorker;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class PeopleServiceImpl implements PeopleService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PeopleServiceImpl.class);

    @Value("${evelopers.file.name}")
    String fileName;

    @Autowired
    ExecutorService executorService;

    @Autowired
    FutureRegistry futureRegistry;

    /**
     * {@inheritDoc}
     */
    @Override
    public String preparePeopleByMonth(Integer monthNumber) {
        LOGGER.info("Start processing of the request for month {}", monthNumber);
        if (monthNumber < Month.JANUARY.getValue() || monthNumber > Month.DECEMBER.getValue()) {
            String message = String.format("Wrong month number passed %s", monthNumber);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
        Future<List<People>> future = executorService.submit(new PeopleWorker(Month.of(monthNumber), fileName));
        String key = futureRegistry.putAndGetKey(future);
        LOGGER.info("Key of processing of the request for month {} is {}", monthNumber, key);
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<People> getRequestResult(String requestId) {
        LOGGER.info("Retrieving the results of request {}", requestId);
        Future<List<People>> future = futureRegistry.get(requestId);
        if (future == null) {
            String message = String.format("Task with ID %s is not registred", requestId);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
        if (!future.isDone()) {
            String message = String.format("Task with ID %s still under construction", requestId);
            LOGGER.error(message);
            throw new StillProcessingException(message);
        }
        List<People> peopleList = new ArrayList<>();
        try {
            peopleList = future.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Cannot get results of execution for key {}", requestId, e);
        }
        LOGGER.info("The results of request {} successfully retrieved. People number is {}", requestId, peopleList.size());
        return peopleList;
    }
}
