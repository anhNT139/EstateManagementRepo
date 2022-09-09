package com.kepler.converter;

import com.kepler.dto.TransactionDTO;
import com.kepler.entity.CustomerEntity;
import com.kepler.entity.TransactionEntity;
import com.kepler.enums.TransactionTypesEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransactionConverter {

    public TransactionDTO convertToDTOFromEntity(TransactionEntity transactionEntity) {

        TransactionDTO transactionDTO = new TransactionDTO();

        transactionDTO.setNote(transactionEntity.getNote());
        transactionDTO.setCodeName(TransactionTypesEnum.valueOf(transactionEntity.getCode()).getTransactionName());
        transactionDTO.setStaffUserName(transactionEntity.getCreatedBy());

        LocalDateTime createdDate = transactionEntity.getCreatedDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        transactionDTO.setCreateTime(formatter.format(createdDate));

        return transactionDTO;
    }

    public TransactionEntity convertToEntityFromDTO(TransactionDTO transactionDTO) {

        TransactionEntity transactionEntity = new TransactionEntity();

        transactionEntity.setCode(transactionDTO.getCode());
        transactionEntity.setNote(transactionDTO.getNote());

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(transactionDTO.getCustomerId());
        transactionEntity.setCustomer(customerEntity);

        return transactionEntity;
    }

}
