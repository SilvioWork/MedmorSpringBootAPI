package com.medmor.SpringBootAPI.service;

import com.medmor.SpringBootAPI.dto.SectiontDTO;
import com.medmor.SpringBootAPI.model.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISectionService {

    public List<SectiontDTO> listSection();

    public Page<SectiontDTO> listSection(Pageable page);

    public SectiontDTO findByIdDto(Long id) throws Exception;

    public Section findById(Long id);

    public SectiontDTO save(Section section);

    public void delete(Long id);

}
