package com.medmor.SpringBootAPI.repository;

import com.medmor.SpringBootAPI.model.ContainerType;
import com.medmor.SpringBootAPI.model.Product;
import com.medmor.SpringBootAPI.model.Section;
import org.hibernate.boot.jaxb.hbm.spi.TypeContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findBySection(Section section);

    public List<Product> findByLot(String lot);

    public List<Product> findByContainerType(ContainerType containerType);

    public List<Product> findByFragile(boolean fragile);

}
