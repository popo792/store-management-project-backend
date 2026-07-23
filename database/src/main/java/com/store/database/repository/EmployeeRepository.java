package com.store.database.repository;

import com.store.database.model.Employee;
import com.store.database.dto.CashierYield;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByRole(String role);

    @Query(value = "EXEC totalProfitPerCashier", nativeQuery = true)
    List<CashierYield> getCashierProfits();
}