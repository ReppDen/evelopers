package ru.repp.den.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.repp.den.FutureRegistry;
import ru.repp.den.dto.People;
import ru.repp.den.exception.BadRequestException;
import ru.repp.den.exception.StillProcessingException;
import ru.repp.den.worker.PeopleWorker;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class PeopleServiceImpl implements PeopleService {

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
        if (monthNumber < Month.JANUARY.getValue() || monthNumber > Month.DECEMBER.getValue()) {
            throw new BadRequestException("Wrong month number");
        }
        Future<List<People>> future = executorService.submit(new PeopleWorker(Month.of(monthNumber), fileName));
        String key = futureRegistry.putAndGetKey(future);
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<People> getRequestResult(String requestId) {
        Future<List<People>> future = futureRegistry.get(requestId);
        if (future == null) {
            throw new BadRequestException(String.format("Task with ID %s is not registred", requestId));
        }
        if (!future.isDone()) {
            throw new StillProcessingException(String.format("Task with ID %s still under construction", requestId));
        }
        List<People> peopleList = new ArrayList<>();
        try {
            peopleList = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return peopleList;
    }
}
