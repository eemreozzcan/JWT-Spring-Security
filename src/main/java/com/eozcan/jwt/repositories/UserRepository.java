package com.eozcan.jwt.repositories;

import com.eozcan.jwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Spring annotation to indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public interface UserRepository extends JpaRepository<User, Long>
{

    User findFirstByEmail(String email); //Find user by email
}
