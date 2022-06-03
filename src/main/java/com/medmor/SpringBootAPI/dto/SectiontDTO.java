package com.medmor.SpringBootAPI.dto;

import com.medmor.SpringBootAPI.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class SectiontDTO {

    private Long id;
    private double size;
    private String typeProduct;
    private List<Product> products;
}
