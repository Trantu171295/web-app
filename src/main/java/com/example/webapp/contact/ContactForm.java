package com.example.webapp.contact;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.example.webapp.CommonForm;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Setter
@Getter
public class ContactForm extends CommonForm implements Serializable {

    private static final long serialVersionUID = 1L;
    /** Original Map */
    Map<String, Object> originalMap = new HashMap<>();
}
