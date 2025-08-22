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
        this.dtoList = dtoList;
        this.pageRequestDTO=pageRequestDTO;
        this.totalCount=(int)totalCnt;

        // 현재 page=7 이라면 end=10, 현재 page=11 -> end=20, 현재 page=23 -> end=30 이렇게 하기 위한 수식
        int end = (int)(Math.ceil(pageRequestDTO.getPage()/10.0))*10;
        int start = end-9;
        int last = (int)(Math.ceil((totalCnt/(double)pageRequestDTO.getSize())));
        end = end > last ? last: end;
        this.prev = start>1;
        this.next = totalCnt > end*pageRequestDTO.getPage();
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        if(prev) this.prevPage = start-1;
        if(next) this.nextPage = end+1;
        this.totalPage = this.pageNumList.size();
        this.current = pageRequestDTO.getPage();
    }
}
