package com.example.easyexchanger.service;

import com.example.easyexchanger.constants.Constants;
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
import java.util.Optional;

@Service
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ExchangeService(ExchangeRepository exchangeRepository, RestTemplate restTemplate) {
        this.exchangeRepository = exchangeRepository;
        this.restTemplate = restTemplate;
    }


    public ExchangeDTO getExchangeData(String exchangeRate) {

        Optional<ExchangeEntity> optionalExchangeEntity = this.exchangeRepository.findFirstByExchangeRateOrderByNextUpdateTimeDesc(exchangeRate.toUpperCase());

        return optionalExchangeEntity.map(exchangeEntity -> {
            if(exchangeEntity.getLastUpdateTime().isBefore(LocalDateTime.now().minusDays(1))){
                return getDataFromExchangeApi(exchangeRate);
            }
            return ExchangeDTO.convert(exchangeEntity);
        }).orElseGet(() -> getDataFromExchangeApi(exchangeRate));
    }


    private ExchangeDTO getDataFromExchangeApi(String exchangeRate) {

        String url = getExchangeRateApiUrl(exchangeRate);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url , String.class);

        try{
            GetExchangeResponse getExchangeResponse = objectMapper.readValue(responseEntity.getBody(), GetExchangeResponse.class);
            ExchangeEntity exchangeEntity = saveExchangeEntity(getExchangeResponse);
            return ExchangeDTO.convert(exchangeEntity);
        }catch (JsonProcessingException ex){
            throw new RuntimeException(ex.getMessage());
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
        exchangeEntity.setDataGettingTime(LocalDateTime.now());
        exchangeEntity.setConversionRates(conversion_rates);

        return exchangeRepository.save(exchangeEntity);
    }
    public String getExchangeRateApiUrl(String exchangeRate){
        return Constants.API_URL + Constants.API_VERSION + Constants.API_KEY + Constants.API_STATUS + exchangeRate;
    }
}
