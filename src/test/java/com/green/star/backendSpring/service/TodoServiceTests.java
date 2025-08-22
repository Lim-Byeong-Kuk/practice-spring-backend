package com.green.star.backendSpring.service;

import com.green.star.backendSpring.dto.PageRequestDTO;
import com.green.star.backendSpring.dto.PageResponseDTO;
import com.green.star.backendSpring.dto.TodoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

@SpringBootTest
@Slf4j
public class TodoServiceTests {

    @Autowired
    private TodoService service;

    @Test
    public void testRegister() {
        TodoDTO dto = TodoDTO.builder()
                .title("서비스테스트")
                .writer("tester")
                .dueDate(LocalDate.of(2025,8,22))
                .build();
        Long tno = service.register(dto);
        log.info("Tno:{}",tno);
    }

    @Test
    @DisplayName("반복문 활영하여 데이터를 10개 넣는데 내용은 다르게")
    public void testRegister10() {

        LongStream.rangeClosed(20,29).mapToObj(
                i -> {
                    TodoDTO dto = TodoDTO.builder()
                            .title("테스트")
                            .writer("tester"+i)
                            .dueDate(LocalDate.of(Integer.parseInt("20"+i),8,22))
                            .build();
                    return  dto;
                }
        ).forEach(dto -> {
            Long tno = service.register(dto);
            log.info("Tno:{}",tno);
        });
    }

    @Test
    public void testRoad() {
        TodoDTO i = service.get(221l);
        log.info("221번 정보 ={}",i);
    }

    @Test
    public void testList() {
        //문1) mysql db 에서 총 데이터 개수를 확인하고
        //page 1~ 그 데이터 갯수만큼 자동으로 반복하여 출력하세요

        // 데이터 131개 -> page 14개, 10개씩
        for(int i=1; i<=14; i++) {
            PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                    .page(i)
                    .size(10)
                    .build();
            PageResponseDTO<TodoDTO> response = service.list(pageRequestDTO);
            log.info("{}페이지 데이터 : {}", i, response.getDtoList());
        }
    }




//    @Test
//    @DisplayName("Quiz")
//    public void quiz1() {
//        //1)서비스 코드를 활용하여 전체 data를 조회하고 각각의 tno를 활용하여
//        //title 정보만 문자열 list 에 담으세요
//
//        //2)title의 문자열의 길이가 3의 배수와 3의 배수가 아닌것들의 문자열 List
//        //Map<String, List<String>> map = newHashMap<>();
//        //{"3의 배수":[ ] , "3의배수가 아님":[ ]}
//        List<String> list = new ArrayList<>();
//        service.list().forEach(i -> list.add(i.getTitle()));
//        log.info("list = {}",list);
//
//        Map<String, List<String>> map = new HashMap<>();
//        List<String> list3Times = list.stream().filter(i -> i.length() % 3 == 0).toList();
//        List<String> listNo3Times = list.stream().filter(i -> i.length() % 3 != 0).toList();
//
//        map.put("3의배수",list3Times);
//        map.put("3의배수아님",listNo3Times);
//
//        log.info("map : {}",map);
//    }

//    @Test
//    @DisplayName("Quiz2")
//    public void quiz2() {
//        //Quiz1 을 for문으로
//        List<TodoDTO> list = service.list();
//        Map<String, List<String>> map = new HashMap<>();
//        List<String> times3 = new ArrayList<>();
//        List<String> noTimes3 = new ArrayList<>();
//
//        for(TodoDTO i :  list) {
//            if(i.getTitle().length()%3==0) {
//                times3.add(i.getTitle());
//            }
//            else {
//                noTimes3.add(i.getTitle());
//            }
//        }
//        map.put("3의배수",times3);
//        map.put("3의배수아님",noTimes3);
//        log.info("map : {}",map);
//    }



}
