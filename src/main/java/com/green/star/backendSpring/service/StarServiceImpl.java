package com.green.star.backendSpring.service;

import com.green.star.backendSpring.domain.Star;
import com.green.star.backendSpring.dto.PageRequestDTO;
import com.green.star.backendSpring.dto.PageResponseDTO;
import com.green.star.backendSpring.dto.StarDTO;
import com.green.star.backendSpring.repository.StarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StarServiceImpl implements StarService{

    @Autowired
    private StarRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public StarDTO find(Long tno) {
        Star star = repository.findById(tno).orElseThrow();
        return mapper.map(star, StarDTO.class);
    }

    @Override
    public Long register(StarDTO dto) {
        StarDTO starDTO = calcAvgTotalGrade(dto);
        Star entity = mapper.map(starDTO, Star.class);
        Star savedStar = repository.save(entity);
        return savedStar.getSno();
    }

    @Override
    public PageResponseDTO<StarDTO> list(PageRequestDTO pageRequestDTO) {
        Pageable pageable =
                PageRequest.of(
                        pageRequestDTO.getPage()-1,
                        pageRequestDTO.getSize(),
                        Sort.by("sno").descending()
                );
        Page<Star> result = repository.findAll(pageable);
        List<StarDTO> dtoList = result.getContent().stream()
                .map(i -> mapper.map(i, StarDTO.class))
                .toList();

        long totalCnt = result.getTotalElements();

        PageResponseDTO<StarDTO> responseDTO = PageResponseDTO.<StarDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCnt(totalCnt)
                .build();

        return responseDTO;
    }

    @Override
    public List<StarDTO> list2() {
        List<StarDTO> listStarDTO = new ArrayList<>();
        repository.findAll()
                .forEach(i -> listStarDTO.add(mapper.map(i,StarDTO.class)));

        return listStarDTO;
    }

    @Override
    public void remove(Long sno) {
        repository.deleteById(sno);
    }

    public StarDTO calcAvgTotalGrade(StarDTO dto) {
        int total = dto.getKorea() + dto.getMath() + dto.getEng();
        dto.setTotal(total);
        float avg = 0;
        String grade="";
        if(total != 0)  {
            avg = total/3.0f;
            dto.setAvg(avg);
        }
        else {
            dto.setAvg(0);
        }

        if(avg >=90) {
            grade="수";
        }else if(avg >=80) {
            grade="우";
        }else if(avg >=70 ) {
            grade="미";
        }else if(avg >=60) {
            grade="양";
        } else {
            grade="가";
        }
        dto.setGrade(grade);
        return dto;
    }
}
