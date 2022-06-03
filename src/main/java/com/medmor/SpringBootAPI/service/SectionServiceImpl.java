package com.medmor.SpringBootAPI.service;

import com.medmor.SpringBootAPI.dto.SectiontDTO;
import com.medmor.SpringBootAPI.model.Section;
import com.medmor.SpringBootAPI.model.TypeProduct;
import com.medmor.SpringBootAPI.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionServiceImpl implements ISectionService{

    @Autowired
    private SectionRepository sectionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SectiontDTO> listSection() {
        return sectionRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SectiontDTO> listSection(Pageable page) {
        return  sectionRepository.findAll(page).map(this::convertEntityToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public SectiontDTO findByIdDto(Long id)  {
        Section section = sectionRepository.findById(id).orElse(null);
        SectiontDTO sectionDto = null;
        if (section != null){
            sectionDto =  convertEntityToDto(section);
        }
        return sectionDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Section findById(Long id) {
        return sectionRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public SectiontDTO save(Section section) {
        return convertEntityToDto(sectionRepository.save(section));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        sectionRepository.deleteById(id);
    }

    private SectiontDTO convertEntityToDto(Section section){
        SectiontDTO sectionDto = new SectiontDTO();
        sectionDto.setId(section.getId());
        sectionDto.setSize(section.getSize());
        sectionDto.setTypeProduct(section.getTypeProduct().name());
        sectionDto.setProducts(section.getProducts());
        return  sectionDto;


    }
}
