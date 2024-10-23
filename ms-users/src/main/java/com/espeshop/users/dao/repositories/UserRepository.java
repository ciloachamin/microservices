package com.espeshop.users.dao.repositories;

import com.espeshop.users.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}