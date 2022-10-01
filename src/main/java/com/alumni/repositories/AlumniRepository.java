package com.alumni.repositories;

import com.alumni.entities.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumniRepository extends JpaRepository<Alumni, Long> {

    Alumni findByEmail(String username);

    Alumni findByEmailOrContact(String email, String contact);

}
