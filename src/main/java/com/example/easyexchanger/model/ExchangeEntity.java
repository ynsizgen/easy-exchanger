package com.example.easyexchanger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;

import java.time.LocalDateTime;
import java.util.Map;


@Collation
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ExchangeEntity {
    @Id
    private String id;

    private String exchangeRate;

    private LocalDateTime lastUpdateTime;

    private LocalDateTime nextUpdateTime;

    private Map<String,Double> conversionRates;
}
