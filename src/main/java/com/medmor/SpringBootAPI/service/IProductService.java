package com.medmor.SpringBootAPI.service;

import com.medmor.SpringBootAPI.dto.ProductDTO;
import com.medmor.SpringBootAPI.model.Product;
import com.medmor.SpringBootAPI.model.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {

    public List<ProductDTO> listProducts();

    public Page<ProductDTO> listProducts(Pageable page);

    public ProductDTO findByIdDto(Long id) throws Exception;

    public Product findById(Long id);

    public ProductDTO save(Product product);

    public void delete(Long id);

    public List<ProductDTO> listBySection(Section section);

    public List<ProductDTO> listByLot(String lot);


}
