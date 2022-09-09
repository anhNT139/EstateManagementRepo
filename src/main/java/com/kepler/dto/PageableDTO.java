package com.kepler.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageableDTO<T> {
    private List<T> items = new ArrayList<>();
    private Long totalItems;
    private Integer currentPage;
    private Integer totalPages;
    private Integer limit;
}
