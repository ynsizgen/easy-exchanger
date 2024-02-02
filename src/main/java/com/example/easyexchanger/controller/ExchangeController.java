package com.example.easyexchanger.controller;

import com.example.easyexchanger.dto.ExchangeDTO;
import com.example.easyexchanger.service.ExchangeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/{exchangeRate}")
    @ResponseStatus(HttpStatus.OK)
    public ExchangeDTO getDataFromExchangeApi(@PathVariable String exchangeRate)  {
        return exchangeService.getDataFromExchangeApi(exchangeRate);
    }
}
