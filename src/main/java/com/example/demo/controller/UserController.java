package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService service;
    
    @GetMapping("/")
	public String selectByPrimaryKey() {		
        return service.selectByPrimaryKey(1l).getName();
	}
}
