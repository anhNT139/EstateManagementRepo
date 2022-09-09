package com.kepler.dto.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorDTO {
    private final HttpStatus httpStatus;
    private final List<String> errors;
    private final LocalDateTime time;
}
