package com.workshop.xbrainvendas.resources;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workshop.xbrainvendas.dto.SellerStatsDTO;
import com.workshop.xbrainvendas.entities.Sale;
import com.workshop.xbrainvendas.services.SaleService;

@RestController
@RequestMapping(value="/sales")
public class SaleResource {
	
	@Autowired
	private SaleService service;
	
	// endpoint de acesso às Vendas
	@GetMapping
	public ResponseEntity<List<Sale>> findAll() {
		List<Sale> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Sale> findById(@PathVariable Long id) {
		Sale obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping(value="/estatisticas")
	public ResponseEntity<List<SellerStatsDTO>> getStatistics(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
		
		List<SellerStatsDTO> list = service.getSellerStatistics(dataInicio, dataFim);
		return ResponseEntity.ok().body(list);
	}

}















