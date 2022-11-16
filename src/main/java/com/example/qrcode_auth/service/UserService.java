package com.example.qrcode_auth.service;

import com.example.qrcode_auth.model.App_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.qrcode_auth.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService implements CRUDServiceInterface<UUID, App_User> {

    /**
     * User repository for SQL query's
     */
    private final UserRepository repository;

    /**
     * Constructor method
     *
     * @param repository, User repository
     */
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    /**
     * Method that returns a list of entities of type T
     *
     * @return List T entities
     */
    @Override
    public List<App_User> getUsers() {
        return repository.findAll();
    }

    /**
     * Method that returns an entity of type T with ID
     *
     * @param uuid@return T entity with specific ID
     * @throws EntityNotFoundException No T entity has ID
     */
    @Override
    public App_User getUserById(UUID uuid) throws EntityNotFoundException {
        return repository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("User with Id: " + uuid + " Not found"));
    }

    /**
     * Mehtod that creates an T entity
     *
     * @param enity@return new T entity with ID
     */
    @Override
    public App_User create(App_User enity) {
        enity.setId(UUID.randomUUID());
        return repository.save(enity);
    }

    /**
     * Mehtod that updates an T entity with a given ID
     *
     * @param uuid
     * @param updatedEntity
     * @return Updated entity
     * @throws EntityNotFoundException No T entity has ID
     */
    @Override
    public App_User update(UUID uuid, App_User updatedEntity) throws EntityNotFoundException {
        repository.findById(uuid).ifPresentOrElse(
                (value) -> {
                    updatedEntity.setId(uuid);
                    repository.save(updatedEntity);
                },
                () -> {
                    throw new EntityNotFoundException("User with Id: " + uuid + " Not found");
                }
        );
        return updatedEntity;
    }


    /**
     * Method that deletes a T entity with a given ID
     */
    @Override
    public void delete(UUID uuid) throws EntityNotFoundException {
        repository.findById(uuid).ifPresentOrElse(
                repository::delete,
                () -> {
                    throw new EntityNotFoundException("User with Id: " + uuid + " Not found");
                }
        );
    }

    public App_User findByEmail(String email) throws EntityNotFoundException {
        return repository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email: " + email + " Not found"));
    }


}
