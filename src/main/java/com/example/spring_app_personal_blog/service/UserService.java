package com.example.spring_app_personal_blog.service;

import com.example.spring_app_personal_blog.entity.User;

public interface UserService {
    User findByUsername(String username);
}
