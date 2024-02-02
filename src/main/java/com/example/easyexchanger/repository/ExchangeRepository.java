package com.example.easyexchanger.repository;

import com.example.easyexchanger.model.ExchangeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExchangeRepository extends MongoRepository<ExchangeEntity, String> {
}
