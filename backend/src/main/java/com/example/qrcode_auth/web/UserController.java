package com.example.qrcode_auth.web;

import com.example.qrcode_auth.service.UserService;
import com.example.qrcode_auth.web.dto.CreateUserDTO;
import com.example.qrcode_auth.web.dto.ListUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/")
public class UserController implements CRUDControllerInterface<ListUserDTO, CreateUserDTO>{

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<ListUserDTO>> getAll() {
        return ResponseEntity.ok(
                userService.getUsers().stream().map(ListUserDTO::new).toList()
        );
    }

    @Override
    public ResponseEntity<ListUserDTO> getById(String id) {
        return ResponseEntity.ok(
                new ListUserDTO(
                        userService.getUserById(UUID.fromString(id))
                )
        );
    }

    @Override
    public ResponseEntity<ListUserDTO> create(CreateUserDTO newEntity) {
        return ResponseEntity.ok(
                new ListUserDTO(
                        userService.create(newEntity.toModel())
                )
        );
    }

    @Override
    public ResponseEntity<ListUserDTO> update(String id, CreateUserDTO newEntity) {
        return ResponseEntity.ok(
                new ListUserDTO(
                        userService.update(UUID.fromString(id),newEntity.toModel())
                )
        );
    }

    @Override
    public ResponseEntity.HeadersBuilder delete(String id) {
        userService.delete(UUID.fromString(id));
        return ResponseEntity.noContent();
    }
}
