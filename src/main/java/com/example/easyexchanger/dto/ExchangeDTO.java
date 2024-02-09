package com.example.easyexchanger.dto;

import com.example.easyexchanger.model.ExchangeEntity;
import java.time.LocalDateTime;
import java.util.Map;

public record ExchangeDTO(
        String exchangeRate,
        LocalDateTime lastUpdateTime,
        LocalDateTime nextUpdateTime,
        LocalDateTime dataGettingTime,
        Map<String,Double> conversionRates
) {
    public static ExchangeDTO convert(ExchangeEntity from) {
        return new ExchangeDTO(
                from.getExchangeRate(),
                from.getLastUpdateTime(),
                from.getNextUpdateTime(),
                from.getDataGettingTime(),
                from.getConversionRates());
    }
}
