package com.green.star.backendSpring.dto;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> { // 브라우저에게 dtoList 를 요청한 페이지와 size에 맞도록 반환
    private List<E> dtoList; //todo  데이터들 이지만 나중에는 Product가 E가 되어 productList가 됨
    private List<Integer> pageNumList;//페이지 번호 목록
    private PageRequestDTO pageRequestDTO; // 2개의 묶음을 하나로 처리하기위해 class로 만들어서
    //멤버변수로 처리함, 페이지 이동 및 검색후 이동시 항상 현재 페이지와 페이지당 갯수가 get 방식으로 따라다녀야
    //초기화 되지 않는다.
    private boolean prev, next; // 이전 페이지가 있는가 없는가? 이전 페이지가 없으면 이전으로 가기 버튼이 없어야 겠죠
    private int totalCount, prevPage, nextPage, totalPage, current;
    //총데이터의 갯수, 이전페이지, 다음 페이지, 총페이지갯수(총 페이지의 갯수를 알아야 하단에 이동할 수 있도록 버튼을 만들수 있다.
    //current는 현재 페이지

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCnt) {
        // 데이터가 들어있는 dtoList
        this.dtoList = dtoList;
        // pageRequestDTO 에는 page 와 size 정보가 들어있음
        this.pageRequestDTO=pageRequestDTO;
        // DB 에서 꺼내온 데이터의 갯수
        this.totalCount=(int)totalCnt;

        // 현재 누를 수 있는 마지막 페이지 1~10이 있다면 10 (현재 블록의 끝 페이지 번호)
        // 현재 page=7 이라면 end=10, 현재 page=11 -> end=20, 현재 page=23 -> end=30 이렇게 하기 위한 수식
        int end = (int)(Math.ceil(pageRequestDTO.getPage()/10.0))*10;
        // 시작 페이지 1~10 까지 있다면 1
        int start = end-9;
        // 전체 데이터 기준으로 계산한 실제 마지막 페이지 번호
        // ex) 전체 데이터 105개, 페이지당 개수(size)= 10일 때, last = Math.ceil(10.5)=11 , 즉 11페이지까지 있다
        int last = (int)(Math.ceil((totalCnt/(double)pageRequestDTO.getSize())));
        // end 가 last 보다 크면 end 를 last 로 맞춰 페이지 번호가 실제 범위를 벗어나지 않도록 조정함
        end = end > last ? last: end;
        // start 가 1보다 크다면 true
        this.prev = start>1;
        // getPate() 를 getSize() 로 수정함
        this.next = totalCnt > end*pageRequestDTO.getSize();
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        if(prev) this.prevPage = start-1;
        if(next) this.nextPage = end+1;
        this.totalPage = this.pageNumList.size();
        this.current = pageRequestDTO.getPage();
    }
}
