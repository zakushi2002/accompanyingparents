package com.webapp.accompanyingparents.view.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorForm {
    private String field;
    private String message;
}