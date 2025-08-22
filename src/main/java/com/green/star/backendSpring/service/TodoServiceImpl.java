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
        log.info("==================");
        Todo todo = modelMapper.map(dto, Todo.class); //dto => entity 변환
        Todo savedTodo = repository.save(todo);
        return savedTodo.getTno();
    }

    @Override
    public TodoDTO get(Long tno) {
        Optional<Todo> result = repository.findById(tno);
        Todo todo = result.orElseThrow();
        return modelMapper.map(todo, TodoDTO.class);
    }

    @Override
    public void modify(TodoDTO dto) {
        Optional<Todo> result = repository.findById(dto.getTno());
        Todo todo = result.orElseThrow();
        todo.changeTitle(dto.getTitle());
        todo.changeDueDate(dto.getDueDate());
        todo.changeComplete(dto.isComplete());

        repository.save(todo);
    }

    @Override
    public void remove(Long tno) {

    }

    @Override
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {

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
