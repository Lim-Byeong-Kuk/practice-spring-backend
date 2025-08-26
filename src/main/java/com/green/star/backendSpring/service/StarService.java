package com.green.star.backendSpring.service;

import com.green.star.backendSpring.dto.PageRequestDTO;
import com.green.star.backendSpring.dto.PageResponseDTO;
import com.green.star.backendSpring.dto.StarDTO;

import java.util.List;

public interface StarService {
    StarDTO find(Long sno);//데이터 하나
    Long register(StarDTO dto); //데이터 등록
    PageResponseDTO<StarDTO> list(PageRequestDTO pageRequestDTO); // 리스트
    List<StarDTO> list2();
    void remove(Long sno);
}
