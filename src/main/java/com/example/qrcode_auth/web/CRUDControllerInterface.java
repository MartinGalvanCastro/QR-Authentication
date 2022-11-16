package com.example.qrcode_auth.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CRUD Controller class
 * @param <DTO> Basic DTO
 * @param <CRDTO> Creation DTO
 */
public interface CRUDControllerInterface<DTO,CRDTO> {

    /**
     * GET ALL request
     * @return
     * <pre>
     *     Response Status Code: 200
     *     Reponse Body: List of DTO
     * </pre>
     */
    @GetMapping()
    ResponseEntity<List<DTO>> getAll();

    /**
     * GET By ID Request
     * @param id, id of the entity
     * @return
     * <pre>
     *     Response Status Code: 200
     *     Reponse Body: DTO with specific ID
     * </pre>
     */
    @GetMapping("/{id}")
    ResponseEntity<DTO> getById(@PathVariable("id") String id);

    /**
     * POST Request
     * @param newEntity, Request Bodu. Valid Creation DTO
     * @return
     * <pre>
     *     Response Status Code: 200
     *     Reponse Body: DTO created
     * </pre>
     */
    @PostMapping("/")
    ResponseEntity<DTO> create(@RequestBody CRDTO newEntity);

    /**
     * PUT Request
     * @param id, Id of the Entity
     * @param newEntity, Request Bodu. Valid Creation DTO
     * @return
     * <pre>
     *     Response Status Code: 200
     *     Reponse Body: DTO updated
     * </pre>
     */
    @PutMapping("/{id}")
    ResponseEntity<DTO> update(@PathVariable("id") String id,
               @RequestBody CRDTO newEntity);


    /**
     * DELETE Request
     * @param id, id of the Entity
     * <pre>
     *     Response Status Code: 204
     *     Reponse Body: No content
     * </pre>
     */
    @DeleteMapping("/{id}")
    ResponseEntity.HeadersBuilder delete(@PathVariable("id") String id);

}
