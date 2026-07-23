package com.store.database.controller;

import com.store.database.dto.CashierYield;
import com.store.database.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/cashiers")
@CrossOrigin(origins = "http://localhost:4200")
public class CashierController {

    private final EmployeeRepository employeeRepository;

    public CashierController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/profits")
    public ResponseEntity<List<CashierYield>> getCashierProfits() {
        List<CashierYield> profits = employeeRepository.getCashierProfits();
        return ResponseEntity.ok(profits);
    }
}