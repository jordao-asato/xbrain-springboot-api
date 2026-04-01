package com.workshop.xbrainvendas.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.workshop.xbrainvendas.entities.Seller;
import com.workshop.xbrainvendas.repositories.SellerRepository;

// classe de configuração
@Configuration
@Profile("test") // app test props
public class TestConfig implements CommandLineRunner{

	@Autowired
	private SellerRepository sellerRepository;

	// executado quando a aplicação for iniciada
	@Override
	public void run(String... args) throws Exception {
		Seller sel1 = new Seller(null, "Sergio");
		Seller sel2 = new Seller(null, "Fernanda");
		
		// salva um array de vendedores
		sellerRepository.saveAll(Arrays.asList(sel1,sel2));
	}
	
}
