package com.ict.springboot.repository;

import com.ict.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

}
