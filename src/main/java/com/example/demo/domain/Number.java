package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum Number {
    ONE     (1),
    TWO     (2),
    THREE   (3);

    private final int value;

    public static Number findByValue(int value) throws NoSuchElementException {
        return Arrays.stream(Number.values())
                .filter(number -> number.value == value)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Number with value [%d] does not exist", value)));
    }
}
