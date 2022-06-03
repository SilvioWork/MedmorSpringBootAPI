package com.medmor.SpringBootAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Section implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1, message = "min is 1 m2")
    private double size;

    @NotNull(message = "Select a Type Product")
    @Enumerated(EnumType.STRING)
    private TypeProduct typeProduct;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "section")
    @JsonIgnoreProperties({"section","hibernateLazyInitializer", "handler"})
    private List<Product> products = new ArrayList<>();

    private static final long serialVersionUID = 1L;
}
