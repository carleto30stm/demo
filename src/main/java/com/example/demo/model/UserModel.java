package com.example.demo.model;

import com.example.demo.dto.DataUser;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Long telefono;
    private Boolean active;

    public UserModel(DataUser user){
    this.active = true;
    }




}
