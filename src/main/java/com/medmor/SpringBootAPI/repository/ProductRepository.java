package com.medmor.SpringBootAPI.repository;

import com.medmor.SpringBootAPI.model.Product;
import com.medmor.SpringBootAPI.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findBySection(Section section);

    public List<Product> findByLot(String lot);

}
