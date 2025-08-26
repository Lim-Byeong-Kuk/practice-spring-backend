package com.green.star.backendSpring.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_star")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class Star {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long sno;

    private int math;
    private int eng;
    private int korea;
    private float total;
    private float avg;
    private String grade;
}
