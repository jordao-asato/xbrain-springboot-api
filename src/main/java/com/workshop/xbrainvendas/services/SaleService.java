package com.workshop.xbrainvendas.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workshop.xbrainvendas.dto.SellerStatsDTO;
import com.workshop.xbrainvendas.entities.Sale;
import com.workshop.xbrainvendas.entities.Seller;
import com.workshop.xbrainvendas.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public List<Sale> findAll() {
		return repository.findAll();
	}
	
	public Sale findById(Long id) {
		Optional<Sale> obj = repository.findById(id);
		return obj.get();
	}
	
	public List<SellerStatsDTO> getSellerStatistics(LocalDate startDate, LocalDate endDate) {
		
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("A data de início não pode ser posterior à data final.");
		}
		
		// cálculo dos dias. A soma de 1 é para o período ser inclusivo
		long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
		
		// busca as vendas no banco
		List<Sale> sales = repository.findBySaleDateBetween(startDate, endDate); 
		
		// agrupa as vendas por vendedor
		Map<Seller, List<Sale>> salesBySeller = sales.stream()
				.collect(Collectors.groupingBy(Sale::getSeller));
		
		// transforma o mapa agrupado na lista de DTOs
		return salesBySeller.entrySet().stream().map(entry -> {
			Seller seller = entry.getKey();
			List<Sale> sellerSales = entry.getValue();
			
			// soma os valores das vendas desse Seller
			BigDecimal total = sellerSales.stream()
					.map(Sale::getAmount)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			
			// divide o total pelos dias
			// RoundingMode.HALF_UP arredonda a segunda casa decimal
			BigDecimal average = total.divide(BigDecimal.valueOf(days), 2, RoundingMode.HALF_UP);
			
			return new SellerStatsDTO(seller.getName(), total, average);
		}).collect(Collectors.toList());
	}
}













