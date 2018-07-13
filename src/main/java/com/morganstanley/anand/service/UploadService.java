package com.morganstanley.anand.service;

import com.morganstanley.anand.model.Indicator;
import com.morganstanley.anand.model.Trade;
import com.morganstanley.anand.validation.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UploadService {

    @Autowired
    ValidatorService validatorService;

    public List<Trade> uploadFile(MultipartFile file) throws IOException {
        BufferedReader br;
        List<Trade> result = new ArrayList<>();
        String line;
        InputStream is = file.getInputStream();
        br = new BufferedReader(new InputStreamReader(is));
        String[] split;
        Optional<List<String>> optional;
        while ((line = br.readLine()) != null) {
            split = line.split("[|][\\s]*");
            Arrays.stream(split).map(s -> s.trim()).collect(Collectors.toList()).toArray(split);
            optional = validatorService.validate(split);
            if (!optional.isPresent()){
                Trade trade = Trade.builder().tradeId(split[0])
                        .stockName(split[1])
                        .brokerCode(split[2])
                        .brokerName(split[3])
                        .quantity(Integer.valueOf(split[4]))
                        .tradeDate(new Date(split[5]))
                        .settlementDate(new Date(split[6]))
                        .indicator(Indicator.valueOf(split[7]))
                        .build();
                result.add(trade);
            }else {
                System.out.println("****************** "+split[0] +" NOT valid ******************");
            }
        }
        return result;
    }
}
