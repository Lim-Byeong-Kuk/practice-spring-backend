package com.green.star.backendSpring.service;

import com.green.star.backendSpring.dto.PageRequestDTO;
import com.green.star.backendSpring.dto.PageResponseDTO;
import com.green.star.backendSpring.dto.TodoDTO;

public interface TodoService {
    Long register(TodoDTO dto);//등록
    TodoDTO get(Long tno);//데이터 하나
    void modify(TodoDTO dto); //수정
    void remove(Long tno); //삭제
//    List<TodoDTO> list();
    PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);
}
