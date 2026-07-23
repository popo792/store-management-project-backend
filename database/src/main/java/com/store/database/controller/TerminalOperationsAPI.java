package com.store.database.controller;

import com.store.database.model.Order;
import com.store.database.model.Employee;
import com.store.database.repository.OrderRepository;
import com.store.database.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/terminal")
@CrossOrigin(origins = "http://localhost:4200")
public class TerminalOperationsAPI {

    private final OrderRepository orderLedger;
    private final EmployeeRepository crewVault;

    public TerminalOperationsAPI(OrderRepository orderLedger, EmployeeRepository crewVault) {
        this.orderLedger = orderLedger;
        this.crewVault = crewVault;
    }

    @GetMapping("/operators")
    public List<Employee> extractRoster() {
        return crewVault.findAll();
    }

    @GetMapping("/manifest/{cipherPin}")
    public List<Order> extractChronicles(@PathVariable Integer cipherPin) {
        return orderLedger.findByEmployeeId(cipherPin);
    }
}