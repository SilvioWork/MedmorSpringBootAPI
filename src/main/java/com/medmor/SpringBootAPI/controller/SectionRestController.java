package com.medmor.SpringBootAPI.controller;

import com.medmor.SpringBootAPI.dto.SectiontDTO;
import com.medmor.SpringBootAPI.model.Section;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class SectionRestController {

    @Autowired
    private ISectionService sectionService;

    @GetMapping("/section")
    @ApiOperation("List all Sections")
    public List<SectiontDTO> listSection() {
        return sectionService.listSection();
    }

    @GetMapping("/section/page/{page}")
    @ApiOperation("List all Sections (Paginator)")
    public Page<SectiontDTO> listSection(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return sectionService.listSection(pageable);
    }


    @GetMapping("/section/{id}")
    @ApiOperation("List all Sections (Paginator)")
    public ResponseEntity<?> sectionById(@PathVariable Long id) {
        SectiontDTO section = null;
        Map<String, Object> response = new HashMap<>();
        try {
            section = sectionService.findByIdDto(id);
        } catch (DataAccessException e) {
            response.put("message", "Error! section not found");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(section == null) {
            response.put("message", "The section with Id: ".concat(id.toString()).concat(" not found in the database"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(section, HttpStatus.OK);
    }

    @PostMapping("/section")
    @ApiOperation("Save Section")
    public ResponseEntity<?> save(@Valid @RequestBody Section section, BindingResult result) {

        SectiontDTO sectionNew = null;
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
            System.out.println(section);
            sectionNew = sectionService.save(section);

        } catch (DataAccessException e) {
            response.put("message", "Error! to realize the insertion of section ");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Successfully! The Section was inserted correctly");
        response.put("section", sectionNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/section/{id}")
    @ApiOperation("Update Section")
    public ResponseEntity<?> update(@Valid @RequestBody Section section, BindingResult result, @PathVariable Long id) {

        Section sectionActual = sectionService.findById(id);

        SectiontDTO sectionUpdated = null;

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "The Field '" + err.getField() +"' "+ err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (sectionActual == null) {
            response.put("message", "Error: Edit Failed, The section ID: "
                    .concat(id.toString().concat(" dont exist in the DB!")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {

            sectionActual.setSize(section.getSize());
            sectionActual.setTypeProduct(section.getTypeProduct());

            sectionUpdated = sectionService.save(sectionActual);

        } catch (DataAccessException e) {
            response.put("message", "Error! The section could not be edited");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El Section is updated correctly!");
        response.put("section", sectionUpdated);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/section/{id}")
    @ApiOperation("Delete Section")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            sectionService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error! Removing the section from the DB.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The Section is removed succefully!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
