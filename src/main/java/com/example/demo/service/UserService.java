package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserMapper mapper;

    //１件取得
	public User selectByPrimaryKey(Long id) {
		return mapper.selectByPrimaryKey(id);
	}
}
