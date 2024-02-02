package com.example.easyexchanger.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ExchangeDTO(
        String exchangeRate,
        LocalDateTime lastUpdateTime,
        LocalDateTime nextUpdateTime,
        Map<String,Double> conversionRates
) {
}
