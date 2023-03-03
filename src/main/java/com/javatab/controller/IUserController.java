package com.javatab.controller;

import com.javatab.domain.entity.User;
import com.javatab.dto.request.UpdateUserRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.validation.Valid;

@RequestMapping("/users")
public interface IUserController {

    @GetMapping()
    ResponseEntity<List<User>> getAllUsers();

    @GetMapping("/{username}")
    ResponseEntity<User> getUserByName(@PathVariable("username") String username);

    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody @Valid UpdateUserRequest updateUserRequest);
}
