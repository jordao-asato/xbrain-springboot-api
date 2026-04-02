package com.workshop.xbrainvendas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.workshop.xbrainvendas.entities.Sale;

public record SaleResponseDTO(Long id, LocalDate saleDate, BigDecimal amount, Long sellerId, String sellerName) {

	public SaleResponseDTO(Sale entity) {
		this(entity.getId(), entity.getSaleDate(), entity.getAmount(), entity.getSeller().getId(),
				entity.getSeller().getName());
	}
}
