package com.green.star.backendSpring.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_todo")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;
    private String title;
    private String writer;
    private boolean complete;
    private LocalDate dueDate;

    //추가
    public void changeTitle(String title) {
        this.title=title;
    }
    public void changeComplete(boolean complete) {
        this.complete = complete;
    }
    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}

