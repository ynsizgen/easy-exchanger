package com.example.easyexchanger.service;

import com.example.easyexchanger.dto.ExchangeDTO;
import com.example.easyexchanger.dto.GetExchangeResponse;
import com.example.easyexchanger.model.ExchangeEntity;
import com.example.easyexchanger.repository.ExchangeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ExchangeService(ExchangeRepository exchangeRepository, RestTemplate restTemplate) {
        this.exchangeRepository = exchangeRepository;
        this.restTemplate = restTemplate;
    }


    String url ="6666846074f457cbd4fb9b70";


    public ExchangeDTO getDataFromExchangeApi(String exchangeRate) {

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://v6.exchangerate-api.com/v6/" + url + "/latest/" + exchangeRate, String.class);

        try{
            GetExchangeResponse getExchangeResponse = objectMapper.readValue(responseEntity.getBody(), GetExchangeResponse.class);
            ExchangeEntity exchangeEntity = saveExchangeEntity(getExchangeResponse);
            return new ExchangeDTO(
                    exchangeEntity.getExchangeRate(),
                    exchangeEntity.getNextUpdateTime(),
                    exchangeEntity.getLastUpdateTime(),
                    exchangeEntity.getConversionRates());
        }catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }

    }

    private ExchangeEntity saveExchangeEntity(GetExchangeResponse response) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z");

        Map<String,Double> conversion_rates = new HashMap<>();
        conversion_rates.putAll(response.conversionRates());

        ExchangeEntity exchangeEntity = new ExchangeEntity();
        exchangeEntity.setExchangeRate(response.baseCode());
        exchangeEntity.setNextUpdateTime(LocalDateTime.parse(response.timeNextUpdateUtc(),formatter));
        exchangeEntity.setLastUpdateTime(LocalDateTime.parse(response.timeLastUpdateUtc(),formatter));
        exchangeEntity.setConversionRates(conversion_rates);

        return exchangeRepository.save(exchangeEntity);
    }

}
