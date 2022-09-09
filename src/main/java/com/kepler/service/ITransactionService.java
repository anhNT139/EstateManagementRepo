package com.kepler.service;

import com.kepler.dto.TransactionDTO;

import java.util.List;
import java.util.Map;

public interface ITransactionService {
    List<TransactionDTO> getTransactions(Long customerId);
    TransactionDTO saveTransaction(TransactionDTO transactionDTO);
    Map<String, String> getTransactionTypeMap();

}
