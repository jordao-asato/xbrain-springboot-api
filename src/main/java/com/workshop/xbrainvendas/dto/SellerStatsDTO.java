package com.workshop.xbrainvendas.dto;

import java.math.BigDecimal;

public record SellerStatsDTO(
		String name,
		BigDecimal totalSales,
		BigDecimal avgDailySales
) {}
