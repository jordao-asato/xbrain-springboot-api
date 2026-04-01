package com.workshop.xbrainvendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workshop.xbrainvendas.entities.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long>{

}
