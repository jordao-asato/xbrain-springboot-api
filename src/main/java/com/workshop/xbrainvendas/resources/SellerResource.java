package com.workshop.xbrainvendas.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workshop.xbrainvendas.entities.Seller;

@RestController
@RequestMapping(value="/sellers")
public class SellerResource {
	
	// endpoint de acesso aos Vendedores
	@GetMapping
	public ResponseEntity<Seller> findAll() {
		Seller sel = new Seller(1L, "Jordao");
		return ResponseEntity.ok().body(sel);
	}

}
