package com.project.logistic_management.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.project.logistic_management.dto.request.ExpensesDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.service.expenses.ExpensesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpensesController {
    private final ExpensesService expensesService;

    @PostMapping("/create")
    public ResponseEntity<Object> createExpenses(@Valid @RequestBody ExpensesDTO dto) {
        return new ResponseEntity<>(
                BaseResponse.ok(expensesService.createExpenses(dto)),
                HttpStatus.CREATED
        );
    }

    @GetMapping()
    public ResponseEntity<Object> getExpenses() {
        return ResponseEntity.ok(
                BaseResponse.ok(expensesService.getExpenses(null))
        );
    }

    @GetMapping("/expenses_of_driver/{id}")
    public ResponseEntity<Object> getExpensesByUserId(@PathVariable Integer id) {
        return ResponseEntity.ok(
                BaseResponse.ok(expensesService.getExpenses(id))
        );
    }
}
