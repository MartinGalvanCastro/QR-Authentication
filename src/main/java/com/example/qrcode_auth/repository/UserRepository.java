package com.example.qrcode_auth.repository;

import com.example.qrcode_auth.model.App_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<App_User,UUID > {
    Optional<App_User> findByEmail(@NonNull String email);



}
