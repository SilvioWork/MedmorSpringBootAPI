package com.medmor.SpringBootAPI.service;

import com.medmor.SpringBootAPI.dto.ProductDTO;
import com.medmor.SpringBootAPI.model.Product;
import com.medmor.SpringBootAPI.model.Section;
import com.medmor.SpringBootAPI.repository.ProductRepository;
import com.medmor.SpringBootAPI.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SectionRepository sectionRepository;


    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> listProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> listProducts(Pageable page) {
        return productRepository.findAll(page).map(this::convertEntityToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findByIdDto(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        ProductDTO productDTO = null;
        if (product != null){
            productDTO =  convertEntityToDto(product);
        }
        return productDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public ProductDTO save(Product product) {
        return convertEntityToDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public void delete(Long id) {
     productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> listBySection(Section section) {
        return productRepository.findBySection(section)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> listByLot(String lot) {
        return productRepository.findByLot(lot)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }


    private ProductDTO convertEntityToDto(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setSize(product.getSize());
        productDTO.setColor(product.getColor());
        productDTO.setPrice(product.getPrice());
        productDTO.setAmount(product.getAmount());
        productDTO.setLot(product.getLot());
        productDTO.setFragile(product.isFragile());
        productDTO.setContainerType(product.getContainerType().name());
        productDTO.setSection(product.getSection());
        return productDTO;
    }
}
