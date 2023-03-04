package com.example.webapp.result;

import java.io.Serializable;

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
public class ResultForm extends CommonForm implements Serializable {

    private static final long serialVersionUID = 1L;
}
