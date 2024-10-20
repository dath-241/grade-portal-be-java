package com.hcmut.gradeportal.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcmut.gradeportal.entities.User;
import com.hcmut.gradeportal.entities.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

    @Query("SELECT cc FROM User cc")
    Set<User> findAlls();

    @SuppressWarnings("null")
    Optional<User> findById( String id);

    User findByEmail(String email);

    void deleteById(@SuppressWarnings("null") String id);

    Optional<User> findByEmailAndRole(String email, Role student);

}
