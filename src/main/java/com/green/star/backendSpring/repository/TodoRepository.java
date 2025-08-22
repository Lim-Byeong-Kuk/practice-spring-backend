package com.green.star.backendSpring.repository;


import com.green.star.backendSpring.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
