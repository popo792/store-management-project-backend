package com.store.database.repository;

import com.store.database.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.math.BigDecimal;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByEmployeeId(Integer queryKey);

    @Query(value = "EXEC totalCustomerOrderValue @OrderId = :orderId", nativeQuery = true)
    BigDecimal totalCustomerOrderValue(@Param("orderId") Integer orderId);

}