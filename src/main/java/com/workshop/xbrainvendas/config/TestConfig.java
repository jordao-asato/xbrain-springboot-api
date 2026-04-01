package com.workshop.xbrainvendas.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.workshop.xbrainvendas.entities.Sale;
import com.workshop.xbrainvendas.entities.Seller;
import com.workshop.xbrainvendas.repositories.SaleRepository;
import com.workshop.xbrainvendas.repositories.SellerRepository;

// classe de configuração
@Configuration
@Profile("test") // app test props
public class TestConfig implements CommandLineRunner{

	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private SaleRepository saleRepository;

	// executado quando a aplicação for iniciada
	@Override
	public void run(String... args) throws Exception {
		Seller sel1 = new Seller(null, "Sergio");
		Seller sel2 = new Seller(null, "Fernanda");
		
		Sale sa1 = new Sale(null, LocalDate.parse("2026-04-01"), new BigDecimal("500.00"), sel1);
		Sale sa2 = new Sale(null, LocalDate.parse("2026-03-29"), new BigDecimal("600.00"), sel2);
		Sale sa3 = new Sale(null, LocalDate.parse("2026-03-31"), new BigDecimal("150.00"), sel1);
		
		// salva um array de vendedores
		sellerRepository.saveAll(Arrays.asList(sel1,sel2));
		saleRepository.saveAll(Arrays.asList(sa1, sa2, sa3));
	}
	
}
