package com.example.demo.controllers;

import com.example.demo.dto.DataResponseUser;
import com.example.demo.dto.UpdateUser;
import com.example.demo.model.UserModel;
import com.example.demo.dto.ListUsers;
import com.example.demo.service.UserServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/users")
public class UserControllers {
    @Autowired
    private UserServiceImpl service;

    @PostMapping
    public ResponseEntity<UserModel> createNewUser(@RequestBody UserModel userModel, UriComponentsBuilder uriComponentsBuilder){
        ResponseEntity<UserModel> responseEntity = service.createUser(userModel, uriComponentsBuilder);
        if (responseEntity.getStatusCode() == HttpStatus.CREATED){
            return responseEntity;
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public ResponseEntity <Page<ListUsers>> getUsers(Pageable pageable){
        return ResponseEntity.ok(service.getUsers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponseUser> getUserById(@PathVariable Long id){
        return service.getUserById(id);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> updateResponseUser(@RequestBody @Valid UpdateUser user){
        return service.updateUser(user);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity <DataResponseUser> deleteUser(@PathVariable Long id){
        service.desactiveUser(id);
        return ResponseEntity.noContent().build();
    }
}
