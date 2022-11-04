package com.example.qrcode_auth.web.dto;

import com.example.qrcode_auth.model.App_User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ListUserDTO extends UserDTO{
    private String id;

    public ListUserDTO(String name, String email, String id, String role) {
        super(name, email, role);
        this.id = id;
    }

    public ListUserDTO(App_User appUser) {
        super(appUser);
        this.id = appUser.getId().toString();
    }

    @Override
    public App_User toModel() {
        App_User temp = super.toModel();
        temp.setId(UUID.fromString(this.id));
        return temp;
    }
}

