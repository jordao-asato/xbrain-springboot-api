package com.workshop.xbrainvendas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.workshop.xbrainvendas.dto.SaleRequestDTO;
import com.workshop.xbrainvendas.dto.SellerStatsDTO;
import com.workshop.xbrainvendas.entities.Sale;
import com.workshop.xbrainvendas.entities.Seller;
import com.workshop.xbrainvendas.repositories.SaleRepository;
import com.workshop.xbrainvendas.repositories.SellerRepository;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {
	
	@InjectMocks
	private SaleService saleService; // classe de teste
	
	@Mock
	private SaleRepository saleRepository;

	@Mock
	private SellerRepository sellerRepository;
	
	private Seller vendedor;
	private Sale venda1;
	private Sale venda2;
	
	@BeforeEach
	void setUp() {
		vendedor = new Seller(1L, "Sergio");
		venda1 = new Sale(1L, LocalDate.parse("2026-03-29"), new BigDecimal("100.00"), vendedor);
		venda2 = new Sale(2L, LocalDate.parse("2026-03-30"), new BigDecimal("200.00"), vendedor);
	}
	
	@Test
	@DisplayName("Deve criar uma venda com sucesso quando o vendedor existir")
	void insert_DeveCriarVendaQuandoVendedorExiste() {
		
		// arrange 
		SaleRequestDTO requestDTO = new SaleRequestDTO(LocalDate.parse("2026-03-31"), new BigDecimal("150.00"), 1L);
		
		when(sellerRepository.findById(1L)).thenReturn(Optional.of(vendedor));
		
		Sale vendaSalva = new Sale(3L, requestDTO.saleDate(), requestDTO.amount(), vendedor);
		when(saleRepository.save(any(Sale.class))).thenReturn(vendaSalva);
		
		// act
		Sale resultado = saleService.insert(requestDTO);
		
		// assert
		assertNotNull(resultado);
		assertEquals(3L, resultado.getId());
		assertEquals(new BigDecimal("150.00"), resultado.getAmount());
		assertEquals("Sergio", resultado.getSeller().getName());
		
		verify(saleRepository).save(any(Sale.class));
	}
	
	@Test
	@DisplayName("Deve lançar exceção ao tentar criar venda para vendedor inexistente")
	void insert_DeveLancarExcecao_QuandoVendedorNaoExiste() {
		
		// arrange
		SaleRequestDTO requestDTO = new SaleRequestDTO(LocalDate.now(), new BigDecimal("100.00"), 99L); // id inexistente
		
		when(sellerRepository.findById(99L)).thenReturn(Optional.empty());
		
		// act, assert
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			saleService.insert(requestDTO);
		});
		
		assertEquals("Vendedor não encontrado. ID: 99", exception.getMessage());
		
	}
	
	@Test
	@DisplayName("Deve calcular corretamente o total e a média de vendas de um vendedor")
	void getSellerStatistics_DeveCalcularMatematicaCorreta() {
		
		// arrange
		LocalDate inicio = LocalDate.parse("2026-03-29");
		LocalDate fim = LocalDate.parse("2026-03-30"); // 2 dias (inclusivo)
		
		List<Sale> vendasDoBanco = Arrays.asList(venda1, venda2); // venda1=100.00, venda2=200.00. total=300.00
		
		when(saleRepository.findBySaleDateBetween(inicio, fim)).thenReturn(vendasDoBanco);

		// act
		List<SellerStatsDTO> estatisticas = saleService.getSellerStatistics(inicio, fim);

		// assert
		assertEquals(1, estatisticas.size()); // Apenas 1 vendedor nas estatísticas
		
		SellerStatsDTO statsSergio = estatisticas.get(0);
		assertEquals("Sergio", statsSergio.name());
		
		// total deve ser 100 + 200 = 300
		assertEquals(new BigDecimal("300.00"), statsSergio.totalSales()); 
		
		// média deve ser 300 / 2 dias = 150
		assertEquals(new BigDecimal("150.00"), statsSergio.avgDailySales()); 
	}
	
	@Test
	@DisplayName("Deve lançar exceção quando a data inicial for maior que a final")
	void getSellerStatistics_DeveLancarExcecao_QuandoDatasInvalidas() {
		
		// arrange
		LocalDate dataInicioErrada = LocalDate.parse("2026-04-10");
		LocalDate dataFim = LocalDate.parse("2026-04-01");

		// act, assert
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			saleService.getSellerStatistics(dataInicioErrada, dataFim);
		});

		assertEquals("A data de início não pode ser posterior à data final.", exception.getMessage());
	}
	
}

















