package com.workshop.xbrainvendas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workshop.xbrainvendas.entities.Sale;
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
	
}
