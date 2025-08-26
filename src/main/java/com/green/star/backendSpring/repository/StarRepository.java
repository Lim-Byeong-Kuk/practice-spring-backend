package com.green.star.backendSpring.repository;

import com.green.star.backendSpring.domain.Star;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star,Long> {
}
