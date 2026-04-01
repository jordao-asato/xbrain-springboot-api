package com.workshop.xbrainvendas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workshop.xbrainvendas.entities.Seller;
import com.workshop.xbrainvendas.repositories.SellerRepository;

@Service
public class SellerService {

	@Autowired
	private SellerRepository repository;
	
	public List<Seller> findAll() {
		return repository.findAll();
	}
	
	public Seller findById(Long id) {
		Optional<Seller> obj = repository.findById(id);
		return obj.get();
	}
	
}
