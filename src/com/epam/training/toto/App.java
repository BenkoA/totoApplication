package com.epam.training.toto;

import com.epam.training.toto.domain.Round;
import com.epam.training.toto.service.TotoService;

import java.text.DecimalFormat;
import java.util.List;

public class App {

    public static void main (String[] args) {

        TotoService totoService = new TotoService();
        List<Round> csvFile = totoService.getCsvFileContent();

        // Task #1
        final int[] maximumPrize = {0};
        csvFile.stream();
        csvFile.forEach(actualRound -> {
            if (actualRound.getHits().get(0).getPrize() > maximumPrize[0])
                maximumPrize[0] = actualRound.getHits().get(0).getPrize();
        });

        String decimalPattern = "###,###,### Ft";
        DecimalFormat decimalFormat = new DecimalFormat(decimalPattern);
        System.out.println("The largest prize ever recorded:" + decimalFormat.format(maximumPrize[0]));
    }
}
