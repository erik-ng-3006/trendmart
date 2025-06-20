package com.example.trendmart.repositories;

import com.example.trendmart.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoleRepository extends JpaRepository<Role, Long> {
   List<Role> findByName(String name);
}
