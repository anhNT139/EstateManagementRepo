package com.kepler.service.impl;

import com.kepler.converter.TransactionConverter;
import com.kepler.dto.TransactionDTO;
import com.kepler.entity.TransactionEntity;
import com.kepler.enums.TransactionTypesEnum;
import com.kepler.repository.TransactionRepository;
import com.kepler.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;


    @Override
    public List<TransactionDTO> getTransactions(Long customerId) {

         return transactionRepository.findByCustomer_Id(customerId, Sort.by(ASC, "createdDate"))
                                     .stream()
                                     .map(transactionConverter::convertToDTOFromEntity)
                                     .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {

        TransactionEntity transactionEntity = transactionConverter.convertToEntityFromDTO(transactionDTO);
        transactionRepository.save(transactionEntity);

        return transactionConverter.convertToDTOFromEntity(transactionEntity);
    }

    @Override
    public Map<String, String> getTransactionTypeMap() {
        return Arrays.stream(TransactionTypesEnum.values()).collect(Collectors.toMap(TransactionTypesEnum::name, TransactionTypesEnum::getTransactionName));
    }

}
