package com.vault.store.repository;

import com.vault.store.domain.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, String> {
}
