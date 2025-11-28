package com.codeit.async.repository;

import com.codeit.async.entiry.ShippingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingHistoryRepository extends JpaRepository<ShippingHistory, Long> {
}

