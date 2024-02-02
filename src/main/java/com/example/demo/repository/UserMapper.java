package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.model.User;

@Mapper
public interface UserMapper {
    //１件取得
	@Select({"SELECT * FROM users",
			 "WHERE id = #{id}"
		})
	User selectByPrimaryKey(Long id);
}
