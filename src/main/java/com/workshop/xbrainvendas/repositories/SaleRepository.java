package com.workshop.xbrainvendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workshop.xbrainvendas.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long>{

}
