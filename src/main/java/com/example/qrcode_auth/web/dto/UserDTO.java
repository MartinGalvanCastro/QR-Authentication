package com.example.qrcode_auth.web.dto;


import lombok.Getter;
import lombok.Setter;
import com.example.qrcode_auth.model.App_User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Getter
@Setter
public class UserDTO implements Serializable {



    @NotNull
    @NotEmpty
    private String name;


    @NotNull
    @NotEmpty
    private String email;

    private String role;

    public UserDTO() {
    }

    public UserDTO(String name, String email,String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public UserDTO(App_User appUser){
        this.name = appUser.getName();
        this.email = appUser.getEmail();
        this.role = appUser.getRole();
    }

    public App_User toModel(){
        return App_User.builder().
                name(this.name).
                email(this.email).
                role(this.getRole()).build();
    }

}
