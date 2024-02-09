package com.example.easyexchanger.repository;

import com.example.easyexchanger.model.ExchangeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ExchangeRepository extends MongoRepository<ExchangeEntity, String> {
    Optional<ExchangeEntity> findFirstByExchangeRateOrderByNextUpdateTimeDesc(String exchangeRate);
}