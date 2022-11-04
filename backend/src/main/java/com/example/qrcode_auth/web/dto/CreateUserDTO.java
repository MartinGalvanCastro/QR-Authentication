package com.example.qrcode_auth.web.dto;


import com.example.qrcode_auth.model.App_User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateUserDTO extends UserDTO {

    @NotNull
    @NotEmpty
    private String password;


    public CreateUserDTO(String name, String email, String role, String password) {
        super(name, email, role);
        this.password = password;
    }

    public CreateUserDTO(App_User appUser) {
        super(appUser);
        this.password = appUser.getPassword();
    }

    @Override
    public App_User toModel() {
        App_User temp = super.toModel();
        temp.setPassword(this.password);
        return temp;
    }
}
