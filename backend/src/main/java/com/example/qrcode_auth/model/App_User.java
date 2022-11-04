package com.example.qrcode_auth.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Class that emulates a basic user
 */
@Table(name="App_Users")
@Entity
@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class App_User implements Serializable {
    /**
     * Atributes
     */
    @Id
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "app_role")
    private String role;

/*

/*    public User(UUID id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public User(UUID id, String name, String email, Role role, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
    }*/

    public App_User(
            UUID id,
            String name,
            String email,
            String password,
            String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        App_User app_user = (App_User) o;
        return id != null && Objects.equals(id, app_user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
