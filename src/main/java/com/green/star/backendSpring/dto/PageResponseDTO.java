package com.green.star.backendSpring.dto;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> { // 브라우저에게 dtoList 를 요청한 페이지와 size에 맞도록 반환
    private List<E> dtoList; //todo  데이터들 이지만 나중에는 Product가 E가 되어 productList가 됨
    private List<Integer> pageNumList;//페이지 버튼의 페이지 번호 목록 예) [1,2,3,4,5 ...]
    private PageRequestDTO pageRequestDTO; //요청된 페이지, 사이즈를 담은 객체
    // 2개의 묶음을 하나로 처리하기위해 class로 만들어서
    //멤버변수로 처리함, 페이지 이동 및 검색후 이동시 항상 현재 페이지와 페이지당 갯수가 get 방식으로 따라다녀야
    //초기화 되지 않는다.
    private boolean hasPrevPageGroup, hasNextPageGroup; // 이전 페이지 블록이 있는지, 다음 페이지 블록이 있는지
    private int totalDataCnt, prevGroupLastPage, nextGroupFirstPage, totalPageCnt, currentPage;
    //totalDataCnt: 전체 데이터 갯수, prevGroupLastPage: 이전 블록의 마지막 페이지, nextGroupFirstPage : 다음 블록의 첫 페이지
    //totalPageCnt: 페이지 수 총 합
    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalDataCount) {
        // 데이터가 들어있는 dtoList
        this.dtoList = dtoList;
        // pageRequestDTO 에는 page 와 size 정보가 들어있음
        this.pageRequestDTO=pageRequestDTO;
        // DB 에서 꺼내온 데이터의 갯수
        this.totalDataCnt=(int)totalDataCount;

        // getPage()가 최소 페이지 값을 강제로 1로 처리하는 방법
        this.currentPage = Math.max(1, pageRequestDTO.getPage());
        // 현재 누를 수 있는 마지막 페이지 1~10이 있다면 10 (현재 블록의 끝 페이지 번호)
        // 현재 page=7 이라면 end=10, 현재 page=11 -> end=20, 현재 page=23 -> end=30 이렇게 하기 위한 수식
        int endOfPageBlock = (int)(Math.ceil(pageRequestDTO.getPage()/10.0))*10;
        // 시작 페이지 1~10 까지 있다면 1
        int startOfPageBlock = endOfPageBlock-9;
        // 전체 데이터 기준으로 계산한 실제 마지막 페이지 번호
        // ex) 전체 데이터 105개, 페이지당 개수(size)= 10일 때, last = Math.ceil(10.5)=11 , 즉 11페이지까지 있다
        int totalPagesCnt = (int)(Math.ceil((totalDataCount/(double)pageRequestDTO.getSize())));
        // end 가 last 보다 크면 end 를 last 로 맞춰 페이지 번호가 실제 범위를 벗어나지 않도록 조정함
        endOfPageBlock = Math.min(endOfPageBlock, totalPagesCnt);
        // 이전 페이지가 존재하면 hasPrevPageGroup = true
        this.hasPrevPageGroup = startOfPageBlock > 1;
        // 다음 페이지가 존재하면 hasNextPageGroup = true
        this.hasNextPageGroup = endOfPageBlock < totalPagesCnt;
        // startOfPageBlock ~ endOfPageBlock 까지의 페이지 번호 목록 생성
        this.pageNumList = IntStream.rangeClosed(startOfPageBlock, endOfPageBlock).boxed().collect(Collectors.toList());
        // 프론트에서 < 이전, 다음 > 버튼 클릭 시 이동할 페이지 지정
        if(hasPrevPageGroup) this.prevGroupLastPage = startOfPageBlock - 1;
        if(hasNextPageGroup) this.nextGroupFirstPage = endOfPageBlock + 1;

        this.totalPageCnt = this.pageNumList.size();

    }
}
