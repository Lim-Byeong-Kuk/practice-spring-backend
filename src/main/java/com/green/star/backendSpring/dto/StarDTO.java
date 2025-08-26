package com.green.star.backendSpring.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class StarDTO {

    private Long sno;
    private int math;
    private int eng;
    private int korea;
    private float total;
    private float avg;
    private String grade;
}
