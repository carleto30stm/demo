package com.example.demo.dto;

import com.example.demo.model.UserModel;

public record ListUsers(Long id, String nombre , String apellido, String email, Long telefono, Boolean active ) {
    public ListUsers(UserModel model){
        this(model.getId(), model.getNombre(), model.getApellido(), model.getEmail(), model.getTelefono(), model.getActive());
    }

}
