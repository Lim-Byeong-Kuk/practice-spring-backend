package com.green.star.backendSpring.controller;

import com.green.star.backendSpring.dto.PageRequestDTO;
import com.green.star.backendSpring.dto.PageResponseDTO;
import com.green.star.backendSpring.dto.StarDTO;
import com.green.star.backendSpring.service.StarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/star")
public class StarController {
    @Autowired
    private StarService service;

    @GetMapping("/{sno}")
    public ResponseEntity<StarDTO> getOne(@PathVariable("sno") Long sno) {
        log.info("star controller sno:{}",sno);
        return ResponseEntity.ok(service.find(sno));
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public Map<String, Long> register(@RequestBody StarDTO dto) {
        log.info("star controller postdto={}",dto);
        Long sno = service.register(dto);
        return Map.of("sno", sno);
    }

    @GetMapping("/list")
    public PageResponseDTO<StarDTO> list(PageRequestDTO dto) {
        log.info("star controller list ");
        return service.list(dto);
    }

    @GetMapping("/list2")
    public List<StarDTO> list2() {
        log.info("star controller list2 ");
        return service.list2();
    }

//    @GetMapping("/delete/{sno}")
//    public List<StarDTO> deleteOne(@PathVariable Long sno) {
//        service.remove(sno);
//        return service.list2();
//    }

    @GetMapping("/delete/{sno}")
    public ResponseEntity<List<StarDTO>> deleteOne(@PathVariable Long sno) {
        service.remove(sno);
        return ResponseEntity.accepted().body(service.list2());
    }

}
