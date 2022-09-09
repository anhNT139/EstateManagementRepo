package com.kepler.utils;

import com.kepler.entity.UserEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ConverterUtils {
    public static String convertToDateTimeStr(LocalDateTime time, String pattern) {
        if (time == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(time);
    }

    public static String getInfoFromUserEntity(UserEntity entity) {
        Optional<String> managerName = Optional.ofNullable(entity.getFullName());
        Optional<String> managerPhone = Optional.ofNullable(entity.getPhone());
        return managerName.orElse("") + managerPhone.map(phone -> " (" + phone + ")").orElse("");
    }
}
