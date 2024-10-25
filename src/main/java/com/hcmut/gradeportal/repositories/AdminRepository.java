package com.hcmut.gradeportal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmut.gradeportal.entities.Admin;
import com.hcmut.gradeportal.entities.enums.Role;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    Optional<Admin> findByEmailAndRole(String email, Role admin);

}
