package com.green.star.backendSpring.service;

import com.green.star.backendSpring.domain.Todo;
import com.green.star.backendSpring.dto.PageRequestDTO;
import com.green.star.backendSpring.dto.PageResponseDTO;
import com.green.star.backendSpring.dto.TodoDTO;
import com.green.star.backendSpring.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor // 생성자 자동 주입
public class TodoServiceImpl implements TodoService{

    //자동 주입 대상은 final로
    private final ModelMapper modelMapper;
    private final TodoRepository repository;

    @Override
    public Long register(TodoDTO dto) {
        log.info("2)서비스 데이터 등록 dto ={}",dto);
        Todo todo = modelMapper.map(dto, Todo.class); //dto => entity 변환
        log.info("3)서비스 데이터 등록 dto 를 entity 로 변환 ={}",todo);
        Todo savedTodo = repository.save(todo);
        log.info("4)서비스 데이터 등록 DB에 반영 후 entity ={}",savedTodo);
        return savedTodo.getTno();
    }

    @Override
    public TodoDTO get(Long tno) {
        log.info("1)서비스 데이터 하나 조회 get tno={}",tno);
        Optional<Todo> result = repository.findById(tno);
        log.info("2)서비스 tno={} 데이터 하나 조회 entity={}",tno,result);
        Todo todo = result.orElseThrow();
        TodoDTO dto = modelMapper.map(todo, TodoDTO.class);
        log.info("3)서비스 데이터 하나 조회하여 dto={}",dto);
        return dto;
    }

    @Override
    public void modify(TodoDTO dto) {
        log.info("2) service 수정 dto ={}",dto);
        Optional<Todo> result = repository.findById(dto.getTno());
        log.info("3) service 수정을 하기 위한 데이터 조회 result ={}",result);
        Todo todo = result.orElseThrow();
        todo.changeTitle(dto.getTitle());
        todo.changeDueDate(dto.getDueDate());
        todo.changeComplete(dto.isComplete());

        repository.save(todo);
    }

    @Override
    public void remove(Long tno) {
        log.info("service remove tno ={}",tno);
        repository.deleteById(tno);
    }

    @Override
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        log.info("전체 조회 service: pageRequestDTO = {}", pageRequestDTO);
        Pageable pageable =
                PageRequest.of(
                        pageRequestDTO.getPage()-1,
                        pageRequestDTO.getSize(),
                        Sort.by("tno").descending()
                );
        Page<Todo> result = repository.findAll(pageable);
        List<TodoDTO> dtoList = result.getContent().stream()
                .map(i -> modelMapper.map(i, TodoDTO.class))
                .toList();

        long totalCnt = result.getTotalElements();

        PageResponseDTO<TodoDTO> responseDTO = PageResponseDTO.<TodoDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCnt(totalCnt)
                .build();

        return responseDTO;
    }

//    @Override
//    public List<TodoDTO> list() {
//        return repository.findAll().stream()
//                .map(i -> modelMapper.map(i, TodoDTO.class)).toList();
//    }
}
