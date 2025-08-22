package com.green.star.backendSpring.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

public class OptionalTests {

    @Test
    public void optionalTest1() {
        Optional<String> em = Optional.empty();
        Assertions.assertFalse(em.isPresent());
    }

    @Test
    public void optionalTest2() {
        Optional<String> em = Optional.of("lbk");
        Assertions.assertTrue(em.isPresent());
    }

    @Test
    public void optionalTest3() {
        Optional<String> em = Optional.of("lbk"); //JPA 가 만들어준다라고 가능
        em.ifPresent(name -> System.out.println(name.length()));
    }

    @Test
    public void optionalTest4() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElse("lbk");
        assertThat(name).isEqualTo("lbk");
    }

}
