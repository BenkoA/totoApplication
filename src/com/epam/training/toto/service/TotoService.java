package com.epam.training.toto.service;

import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TotoService {

    public List<Round> getCsvFileContent() {
        String inputFilePath = "toto.csv";
        List<Round> inputList = new ArrayList<Round>();
        try{
            File inputF = new File(inputFilePath);
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
            inputList = br.lines().map(mapToItem).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return inputList ;
    }

    private Function<String, Round> mapToItem = (line) -> {
        String[] dataFromCsvLine = line.split(";");

        Round item = new Round();
        item.setYear(Integer.parseInt(dataFromCsvLine[0]));
        item.setWeek(Integer.parseInt(dataFromCsvLine[1]));

        item.setRoundOfWeek(this.formatCsvRoundOfWeek(dataFromCsvLine[2]));

        // Date
        if (dataFromCsvLine[3].length() >1) {
            String[] csvDate = dataFromCsvLine[3].split("\\.");
            item.setDate(LocalDate.parse(csvDate[0] + "-" + csvDate[1] + "-" + csvDate[2]));
        }
        else {
            item.setDate(LocalDate.now());
        }

        List<Hit> hits = new ArrayList<Hit>();
        hits.add(new Hit(14, Integer.parseInt(dataFromCsvLine[4]), this.formatCsvWager(dataFromCsvLine[5])));
        hits.add(new Hit(13, Integer.parseInt(dataFromCsvLine[6]), this.formatCsvWager(dataFromCsvLine[7])));
        hits.add(new Hit(12, Integer.parseInt(dataFromCsvLine[8]), this.formatCsvWager(dataFromCsvLine[9])));
        hits.add(new Hit(11, Integer.parseInt(dataFromCsvLine[10]), this.formatCsvWager(dataFromCsvLine[11])));
        hits.add(new Hit(10, Integer.parseInt(dataFromCsvLine[12]), this.formatCsvWager(dataFromCsvLine[13])));
        item.setHits(hits);

        List<Outcome> outcomes = new ArrayList<Outcome>();
        for (int i =14; i <= 26; i++) {
            if (dataFromCsvLine[i].contains("1")){
                outcomes.add(Outcome._1);
            }
            else if (dataFromCsvLine[i].contains("2")) {
                outcomes.add(Outcome._2);
            }
            else if (dataFromCsvLine[i].contains("X")) {
                outcomes.add(Outcome.X);
            }
        }
        item.setOutcomes(outcomes);

        return item;
    };

    private int formatCsvRoundOfWeek(String roundOfWeek) {
        if(roundOfWeek.contains("-")){
            return 1;
        }
        else {
            return Integer.parseInt(roundOfWeek);
        }

    }

    private int formatCsvWager(String wager) {
        String newWager = wager.replaceAll("\\s+", "");
        newWager = newWager.replaceAll("[a-zA-Z]+", "");
        return Integer.parseInt(newWager);
    }
}
