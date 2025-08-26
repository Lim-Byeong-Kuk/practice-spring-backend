package com.green.star.backendSpring.controller;

import com.green.star.backendSpring.dto.PageRequestDTO;
import com.green.star.backendSpring.dto.PageResponseDTO;
import com.green.star.backendSpring.dto.TodoDTO;
import com.green.star.backendSpring.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService service;

    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable(name = "tno") Long tno) {
        log.info("get controller tno={}",tno);
        return service.get(tno);
    }

    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO dto) {
        log.info("list controller dto={}", dto);
        return service.list(dto);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDTO dto) {
        log.info("1)등록 controller dto={}",dto);
        Long tno = service.register(dto);
        return Map.of("tno",tno);
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(
            @PathVariable(name = "tno") Long tno,
            @RequestBody TodoDTO dto
    ) {
        log.info("1) 수정 controller tno={}, dto={}",tno,dto);
        dto.setTno(tno);
        service.modify(dto);
        return Map.of("RESULT","성공");
    }

    @DeleteMapping("/{tno}")
    public Map<String, String> remove(
            @PathVariable(name = "tno") Long tno
    ) {
        log.info("controller 삭제 : tno ={}",tno);
        service.remove(tno);
        return Map.of("RESULT","성공");
    }
}
