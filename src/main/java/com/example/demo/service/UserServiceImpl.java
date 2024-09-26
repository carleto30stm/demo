package com.example.demo.service;

import com.example.demo.dto.DataResponseUser;
import com.example.demo.dto.ListUsers;
import com.example.demo.dto.UpdateUser;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserRepository repository;

    @Override
    public ResponseEntity<UserModel> createUser(UserModel userModel, UriComponentsBuilder uriComponentsBuilder) {
        UserModel user =  repository.save(userModel);
        URI url = uriComponentsBuilder.path("/api/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(url).body(user);
    }

    @Override
    public Page<ListUsers> getUsers(Pageable pageable) {

        return repository.findAll(pageable).map(ListUsers ::new);
    }
    @Override
    public ResponseEntity<DataResponseUser> getUserById(Long id){
        UserModel userById  = repository.getReferenceById(id);
        return ResponseEntity.ok(new DataResponseUser(userById.getNombre(), userById.getApellido()));
    }

    @Override
    public ResponseEntity<?> updateUser(@Valid  UpdateUser updateUser) {
        UserModel user = repository.findById(updateUser.id()).orElseThrow(() -> new EntityNotFoundException("El usuario con el "+ updateUser.id()+" no existe"));

        if (updateUser.nombre() != null) {
            user.setNombre(updateUser.nombre());
        }
        if (updateUser.apellido() != null) {
            user.setApellido(updateUser.apellido());
        }
        if (updateUser.email() != null) {
            user.setEmail(updateUser.email());
        }
        if (updateUser.telefono() != null) {
            user.setTelefono(updateUser.telefono());
        }

        repository.save(user);

        return ResponseEntity.ok(new DataResponseUser(
                user.getNombre(),
                user.getApellido()
        ));
    }

    @Override
    public void desactiveUser(Long id) {
        UserModel user = repository.getReferenceById(id);
        user.setActive(false);
    }
}
