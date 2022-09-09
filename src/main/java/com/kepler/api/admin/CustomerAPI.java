package com.kepler.api.admin;

import com.kepler.dto.staff.AssignmentDTO;
import com.kepler.dto.customer.CustomerDTO;
import com.kepler.dto.TransactionDTO;
import com.kepler.dto.staff.StaffDTO;
import com.kepler.service.IAssignmentService;
import com.kepler.service.ICustomerService;
import com.kepler.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/customer")
@RequiredArgsConstructor
public class CustomerAPI {
    private final ICustomerService customerService;
    private final IAssignmentService assignmentService;
    private final ITransactionService transactionService;

    @PutMapping()
    public ResponseEntity<Long> updateCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.saveCustomer(customerDTO));
    }

    @PostMapping()
    public ResponseEntity<Long> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.saveCustomer(customerDTO));
    }

    @PostMapping(value = "/assignment")
    public ResponseEntity<List<StaffDTO>> assignCustomer(@RequestBody @Valid AssignmentDTO assignmentCustomerDTO) {
        return ResponseEntity.ok(assignmentService.assignCustomerToStaff(assignmentCustomerDTO));
    }

    @PostMapping(value = "/transaction")
    public ResponseEntity<TransactionDTO> addTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.saveTransaction(transactionDTO));
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteCustomers(@RequestBody List<Long> customersId) {
        return ResponseEntity.ok(customerService.removeCustomers(customersId));
    }
}
