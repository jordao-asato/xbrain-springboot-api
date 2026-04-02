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

import com.workshop.xbrainvendas.dto.SaleRequestDTO;
import com.workshop.xbrainvendas.dto.SellerStatsDTO;
import com.workshop.xbrainvendas.entities.Sale;
import com.workshop.xbrainvendas.entities.Seller;
import com.workshop.xbrainvendas.repositories.SaleRepository;
import com.workshop.xbrainvendas.repositories.SellerRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	public List<Sale> findAll() {
		return repository.findAll();
	}
	
	public Sale findById(Long id) {
		Optional<Sale> obj = repository.findById(id);
		return obj.get();
	}
	
	public Sale insert(SaleRequestDTO dto) {
		Seller seller = sellerRepository.findById(dto.sellerId())
				.orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado. ID: " + dto.sellerId()));
		
		Sale sale = new Sale();
		sale.setSaleDate(dto.saleDate());
		sale.setAmount(dto.amount());
		sale.setSeller(seller);
		
		return repository.save(sale);
	}
	
	public List<SellerStatsDTO> getSellerStatistics(LocalDate startDate, LocalDate endDate) {
		
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("A data de início não pode ser posterior à data final.");
		}
		
		long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
		List<Sale> sales = repository.findBySaleDateBetween(startDate, endDate); 
		
		Map<Seller, List<Sale>> salesBySeller = sales.stream()
				.collect(Collectors.groupingBy(Sale::getSeller));

		return salesBySeller.entrySet().stream().map(entry -> {
			Seller seller = entry.getKey();
			List<Sale> sellerSales = entry.getValue();
		
			BigDecimal total = sellerSales.stream()
					.map(Sale::getAmount)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			
			BigDecimal average = total.divide(BigDecimal.valueOf(days), 2, RoundingMode.HALF_UP);
			
			return new SellerStatsDTO(seller.getName(), total, average);
		}).collect(Collectors.toList());
	}
}













