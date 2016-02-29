package ru.repp.den.worker;

import ru.repp.den.dto.People;

import java.io.FileInputStream;
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

    public static final String CSV_DELIMETER = ",";

    private final Month month;
    private final String fileName;

    public PeopleWorker(Month m, String fileName) {
        this.month = m;
        this.fileName = fileName;
    }

    @Override
    public List<People> call() throws Exception {
        List<People> res = new ArrayList<>();
        Path p = Paths.get(fileName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Files.lines(p).forEach(s -> {
            String[] parts = s.split(CSV_DELIMETER);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<String> values = Stream.of(parts).map(String::trim).collect(Collectors.toList());
            LocalDate d = LocalDate.parse(values.get(1), formatter);
            if (month.getValue() == d.getMonthValue()) {
                // correct month, save it
                res.add(new People(values.get(0), d));
            }
        });
        return res;
    }


}
