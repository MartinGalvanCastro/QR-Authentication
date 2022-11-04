package com.example.qrcode_auth.service;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Interface for basic CRUD Services
 * @param <ID> ID class for the entity
 * @param <T> Entity class
 */
public interface CRUDServiceInterface<ID,T>{

    /**
     * Method that returns a list of entities of type T
     * @return List T entities
     */
    @Transactional
    List<T> getUsers() throws AuthenticationException;


    /**
     * Method that returns an entity of type T with ID
     * @param id, type ID, id requested
     * @return T entity with specific ID
     * @throws EntityNotFoundException No T entity has ID
     */
    @Transactional
    T getUserById(ID id) throws EntityNotFoundException;


    /**
     * Mehtod that creates an T entity
     * @param enity, with it values
     * @return new T entity with ID
     */
    @Transactional
    T create(T enity);

    /**
     * Mehtod that updates an T entity with a given ID
     * @param id, id of the entity
     * @param updatedEntity, entity with updates values
     * @return Updated entity
     * @throws EntityNotFoundException No T entity has ID
     */
    @Transactional
    T update(ID id, T updatedEntity) throws EntityNotFoundException;

    /**
     * Method that deletes a T entity with a given ID
     * @param id, ID of the T entity
     */
    @Transactional
    void delete(ID id);

}
