package com.medmor.SpringBootAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "cannot be empty")
    @NotEmpty(message = "cannot be Empty")
    private String size;

    @NotNull(message = "cannot be Null")
    @NotEmpty(message = "cannot be Empty")
    private String color;

    @NotNull(message = "cannot be Null")
    private double price;

    @NotNull(message = "cannot be Null")
    private double amount;

    private boolean fragile;

    @NotNull(message = "cannot be Null")
    @NotEmpty(message = "cannot be Empty")
    private String lot;

    @NotNull(message = "cannot be Null")
    @Enumerated(EnumType.STRING)
    private ContainerType containerType;

    @NotNull(message = "cannot be Null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"products","hibernateLazyInitializer", "handler"})
    private Section section;

    private static final long serialVersionUID = 1L;
}
