package com.cherkashyn.vitalii.export.documentscanner.persistent.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findByOrderId(String orderId);
}
