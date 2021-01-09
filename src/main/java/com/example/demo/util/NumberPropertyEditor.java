package com.example.demo.util;

import com.example.demo.controller.Number;

import java.beans.PropertyEditorSupport;
import java.util.NoSuchElementException;

public class NumberPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            this.setValue(Number.findByValue(Integer.parseInt(text)));

        } catch (NumberFormatException | NoSuchElementException e) {
            throw new IllegalArgumentException(text);
        }
    }
}
