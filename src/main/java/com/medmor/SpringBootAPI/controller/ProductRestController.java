package com.medmor.SpringBootAPI.controller;


import com.medmor.SpringBootAPI.dto.ProductDTO;
import com.medmor.SpringBootAPI.model.ContainerType;
import com.medmor.SpringBootAPI.model.Product;
import com.medmor.SpringBootAPI.model.Section;
import com.medmor.SpringBootAPI.service.IProductService;
import com.medmor.SpringBootAPI.service.ISectionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ProductRestController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ISectionService sectionService;


    @GetMapping("/product")
    @ApiOperation("List all Products")
    public List<ProductDTO> listProduct() {
        return productService.listProducts();
    }

    @GetMapping("/product/page/{page}")
    @ApiOperation("List all Products(Paginator)")
    public Page<ProductDTO> listProduct(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return productService.listProducts(pageable);
    }

    @GetMapping("/product/{id}")
    @ApiOperation("Product By ID")
    public ResponseEntity<?> productById(@PathVariable Long id) {
        ProductDTO productDTO = null;
        Map<String, Object> response = new HashMap<>();
        try {
            productDTO = productService.findByIdDto(id);
        } catch (DataAccessException e) {
            response.put("message", "Error! product not found");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(productDTO == null) {
            response.put("message", "The product with Id: ".concat(id.toString()).concat(" not found in the database"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping("/product")
    @ApiOperation("Save Product")
    public ResponseEntity<?> save(@Valid @RequestBody Product product, BindingResult result) {

        ProductDTO productNew = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors= result.getFieldErrors()
                    .stream()
                    .map(err -> "The Filed "+ err.getField() +" "+ err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            product.setLot(product.getLot().toUpperCase());
            productNew = productService.save(product);

        } catch (DataAccessException e) {
            response.put("message", "Error! to realize the insertion of product ");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Successfully! The Product was inserted correctly");
        response.put("product", productNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/product/{id}")
    @ApiOperation("Updates Product")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id) {

        Product productActual = productService.findById(id);

        ProductDTO productUpdated = null;

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "The Field '" + err.getField() +"' "+ err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (productActual == null) {
            response.put("message", "Error: Edit Failed, The Product ID: "
                    .concat(id.toString().concat(" dont exist in the DB!")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {

            productActual.setSize(product.getSize());
            productActual.setColor(product.getColor());
            productActual.setPrice(product.getPrice());
            productActual.setAmount(product.getAmount());
            productActual.setLot(product.getLot().toUpperCase());
            productActual.setFragile(product.isFragile());
            productActual.setContainerType(product.getContainerType());
            productActual.setSection(product.getSection());

            productUpdated = productService.save(productActual);

        } catch (DataAccessException e) {
            response.put("message", "Error! The product could not be edited");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The Product is updated correctly!");
        response.put("product", productUpdated);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/product/{id}")
    @ApiOperation("Delete Product")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            productService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error! Removing the product from the DB.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The Product is removed succefully!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/product/section/{id}")
    @ApiOperation("List Products by Section")
    public ResponseEntity<?> listBySection(@PathVariable Long id){
        Section section = sectionService.findById(id);
         Map<String,Object> response = new HashMap<>();
        if(section==null){
            response.put("message", "The Section dont exist!" );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<ProductDTO> list = null;

        try {
            list = productService.listBySection(section);

        }catch (DataAccessException e){
            response.put("message", "Error! Find the product from the DB.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/product/lot/{lot}")
    @ApiOperation("List Products by Lot")
    public ResponseEntity<?> listByLot(@PathVariable String lot){
        List<ProductDTO> list = null;
        Map<String,Object> response = new HashMap<>();

        try {

            list = productService.listByLot(lot.toUpperCase());

        }catch (DataAccessException e){
            response.put("message", "Error! Find the product from the DB.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(list.isEmpty()){
            response.put("message", "The Product with this lot dont exist!" );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/product/container/{type}")
    @ApiOperation("List Products by Container Type")
    public ResponseEntity<?> listByContainerType(@PathVariable String type){
        List<ProductDTO> list = null;
        Map<String,Object> response = new HashMap<>();
        ContainerType container = ContainerType.valueOf(type);
        try {
            list = productService.listByContainerType(container);

        }catch (DataAccessException e){
            response.put("message", "Error! Find the product from the DB.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(list.isEmpty()){
            response.put("message", "The Product with this container type dont exist!" );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/product/fragile/{fragile}")
    @ApiOperation("List Products by Container Type")
    public ResponseEntity<?> listByContainerType(@PathVariable boolean fragile){
        List<ProductDTO> list = null;
        Map<String,Object> response = new HashMap<>();
        try {
            list = productService.listByFragile(fragile);

        }catch (DataAccessException e){
            response.put("message", "Error! Find the product from the DB.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(list.isEmpty()){
            response.put("message", "The Product with this value dont exist!" );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }



}
