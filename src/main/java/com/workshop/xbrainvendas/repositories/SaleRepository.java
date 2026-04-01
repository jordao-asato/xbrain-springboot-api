package com.workshop.xbrainvendas.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workshop.xbrainvendas.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long>{
	
	List<Sale> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);

}
