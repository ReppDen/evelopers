package ru.repp.den.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.repp.den.dto.People;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PeopleWorker implements  Callable<List<People>> {

    public static final Logger LOGGER = LoggerFactory.getLogger(PeopleWorker.class);

    private static final String CSV_DELIMETER = ",";

    private final String fileName = "data.txt";

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Month month;

    public PeopleWorker(Month m) {
        this.month = m;
    }

    @Override
    public List<People> call() throws Exception {
        List<People> res = new ArrayList<>();
        Path p = Paths.get(this.getClass().getResource(fileName).toURI());
        Files.lines(p).forEach(s -> {
            String[] parts = s.split(CSV_DELIMETER);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.error("Something went wrong while thread slept",e);
            }
            List<String> values = Stream.of(parts).map(String::trim).collect(Collectors.toList());
            LocalDate d = LocalDate.parse(values.get(1), FORMATTER);
            if (month.getValue() == d.getMonthValue()) {
                // correct month, save it
                res.add(new People(values.get(0), d));
            }
        });
        return res;
    }


}
