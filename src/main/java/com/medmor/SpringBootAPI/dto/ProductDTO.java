package com.medmor.SpringBootAPI.dto;

import com.medmor.SpringBootAPI.model.Section;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String size;
    private String color;
    private double price;
    private boolean isFragile;
    private double amount;
    private String lot;
    private String containerType;
    private Section section;

}
