package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UpdateUser(@Valid @NotNull Long id, String nombre, String apellido, String email, Long telefono ) {
}
