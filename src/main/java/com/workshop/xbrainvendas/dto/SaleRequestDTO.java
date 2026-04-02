package com.workshop.xbrainvendas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaleRequestDTO(LocalDate saleDate, BigDecimal amount, Long sellerId) {
	
}
