package com.example.webapp.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MainValidator implements Validator {

    @Autowired
    protected MessageSource msg;

    @Override
    public boolean supports(Class<?> clazz) {
        return MainForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object Objform, Errors errors) {
        // チェックなし
    }
}
