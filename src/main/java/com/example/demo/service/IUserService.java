package com.example.demo.service;

import com.example.demo.dto.DataResponseUser;
import com.example.demo.dto.ListUsers;
import com.example.demo.dto.UpdateUser;
import com.example.demo.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface IUserService {
    ResponseEntity<DataResponseUser> createUser(UserModel userModel, UriComponentsBuilder uriComponentsBuilder);

    Page<ListUsers> getUsers(Pageable pageable);

    ResponseEntity <DataResponseUser> getUserById(Long id);

    ResponseEntity updateUser(UpdateUser updateUser);

    void desactiveUser(Long id);

}
