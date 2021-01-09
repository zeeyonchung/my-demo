package com.example.demo.advice;

import com.example.demo.domain.Number;
import com.example.demo.request.NumberPropertyEditor;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class NumberPropertyControllerAdvice {

    @InitBinder
    public void numberPropertyInitBinder(DataBinder dataBinder) {
        dataBinder.registerCustomEditor(Number.class, new NumberPropertyEditor());
    }
}
