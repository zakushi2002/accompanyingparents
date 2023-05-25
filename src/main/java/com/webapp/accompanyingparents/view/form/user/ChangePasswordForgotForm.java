package com.webapp.accompanyingparents.view.form.user;

import lombok.Data;

@Data
public class ChangePasswordForgotForm {
    private String email;
    private String newPassword;
}