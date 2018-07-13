package com.morganstanley.anand.controllers;

import com.morganstanley.anand.model.Trade;
import com.morganstanley.anand.repository.TradeRepository;
import com.morganstanley.anand.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TradeController {

    @Autowired
    UploadService service;
    @Autowired
    TradeRepository repository;

    @PostMapping(value = "/trade", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void conductTrade(@RequestParam("file") MultipartFile file) throws IOException {
        List<Trade> trades = service.uploadFile(file);
        for (Trade trade : trades) {
            Optional<Trade> optional = repository.findById(trade.getTradeId());
            if (!optional.isPresent()) repository.save(trade);
            else System.out.println("****************** "+optional.get().getTradeId()+" already found ******************");
        }
    }

    @GetMapping(value = "/trade/{broker_code}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Trade> tradeBook(@PathVariable(value = "broker_code") String brokerCode){
        return repository.findByBrokerCode(brokerCode);
    }

    @GetMapping(value = "/trade/top" , consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<String> topTrade(){
        List<String> list = repository.findTopStock();
        if (list.size()>5) return list.subList(0,5);
        else return list;
    }

}
