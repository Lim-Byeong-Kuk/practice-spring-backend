package com.green.star.backendSpring.repository;

import com.green.star.backendSpring.domain.Todo;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void test1() {
        System.out.println("----");
        System.out.println(todoRepository);
    }

    @Test
    @DisplayName("데이터 100개 인서트")
    public void testInsert() {
        for (int i=0; i<100; i++) {
            Todo todo = Todo.builder()
                    .title("Title..." +i)
                    .dueDate(LocalDate.of(2023,12,31))
                    .writer("user"+i)
                    .build();
            todoRepository.save(todo); // db에 저장됨
        }
    }

    @Test
    public void testInsert2() {

        boolean flag= true;
        LongStream.rangeClosed(400,600).mapToObj(
                i -> new Todo(
                        null,
                        "제목"+i,
                        "LBK"+i,
                        !flag,
                        LocalDate.of(2023,12,31)))
                .forEach(j -> todoRepository.save(j));

    }

    @Test
    public void testRead() {
        Long tno = 33L;
        List<String> writers = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        Optional<Todo> result = todoRepository.findById(tno);
        //Optional 은 자바에서 null처리, try catch 해줘야함 원래


        //문1) tbl_todo 에서 writer 의 데이터만 writers 에 담기
        todoRepository.findAll().forEach(i -> {
            writers.add(i.getWriter());
        });
        // enhanced for 문으로 변경
//        for(Todo i : todoRepository.findAll()) {
//            writers.add(i.getWriter());
//        }
        log.info(writers);

        //문2) {"writer": [ 작성자들의 모임]}
        map.put("writers",writers);
        log.info(map);
    }

    @Test
    public void testModify() {
        Long tno = 33l;
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();//예외가 있으면 Throw 함 , 예외가 없으면 데이터(Todo)를 가져옴
        todo.changeTitle("오늘은 행복한 월요일");
        todo.changeComplete(true);
        todo.changeDueDate(LocalDate.of(2025,8,11));
        todoRepository.save(todo); // 수정도 저장과 동일한 메서드 사용
    }

    @Test
    @DisplayName("전체 데이터를 가져와 수정해보기")
    public void allDataModify() {

        todoRepository.findAll().forEach((i) -> {
            i.changeTitle("Study Spring in Summer"+i.getTno());
            todoRepository.save(i);
        });
    }

    @Test
    @DisplayName("전체 데이터를 가져와 수정해보기 By선생님")
    public void allDataModifyByTeacher() {

        String[] str ={"가","나","다","라","마","바","사","아","자","차","카","타","파","하"};

        todoRepository.findAll().forEach((i) -> {
            String m ="";
            for(int j=0; j<(int)(Math.random()*20)+1; j++) {
                m+=str[(int)(Math.random()*str.length)];
            }
            i.changeTitle(m);
            todoRepository.save(i);
        });
    }


    @Test
    @DisplayName("모든 데이터를 삭제")
    public void testAllDelete() {

//        todoRepository.deleteById(1L);
        // 전체 데이터 삭제
        // 전체 데이터 가져와서 tno 가 필요하네:??

        todoRepository.findAll().forEach( i -> {
            todoRepository.deleteById(i.getTno());
        });
    }

    @Test
    @DisplayName("tno 가 3의 배수인 모든 데이터를 삭제")
    public void test3BaeSuDelete() {

        todoRepository.findAll().stream().filter(j->j.getTno()%3==0).forEach( i -> {
            todoRepository.deleteById(i.getTno());
        });
    }

    @Test
    @DisplayName("tno 가 3의 배수인 모든 데이터를 삭제 (Stream)안쓰고")
    public void test3BaeSuDeleteNoStream() {

        for(Todo i :todoRepository.findAll()) {
            if(i.getTno()%3==0) {
                todoRepository.deleteById(i.getTno());
            }
        }
    }


    //문2) writer 의 굴자수를 random 하게 추가하고 100
    //writer 의 문자열의 길이가 짝수인것들만 삭제
    static public String randomWriter() {
        String[] sArr = {"가","나","다","라","마","바","사"};
        String s="";
        for(int j=0; j<Math.floor(Math.random()*10+1); j++) {
            s+= sArr[(int)(Math.random()*sArr.length)];
        }
        return s;
    }
    @Test
    @DisplayName("문제2")
    public void testQuestion2() {
        //데이터 Insert
        for(int i=0;i<100; i++) {
            Todo todo = Todo.builder()
                    .title("title.."+i)
                    .writer(randomWriter())
                    .complete(true)
                    .dueDate(LocalDate.of(2023,4,12))
                    .build();
            todoRepository.save(todo);
        }

        //삭제
        todoRepository.findAll().stream().filter(i -> i.getWriter().length()%2==0)
                .forEach(j -> todoRepository.deleteById(j.getTno()));
    }

    @Test
    public void testPaging() {
        Pageable pageable = PageRequest.of(2,10, Sort.by("tno").descending());
        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result.getTotalElements());
        result.getContent().stream().forEach(i -> log.info(i));
    }

    @Test
    public void testrrr() {
        List<String> list = null;
        assertThrows(NullPointerException.class , () -> {
            list.add("사랑");
        });
    }

    @Test
    @DisplayName("없는 Id로 조회시 빈 Optional을 반환한다.")
    public void t() {
        Optional<Todo> optional = todoRepository.findById(500L);
        assertThat(optional).isEmpty();
    }



}
