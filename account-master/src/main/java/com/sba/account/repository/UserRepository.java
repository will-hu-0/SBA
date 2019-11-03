package com.sba.account.repository;

import com.sba.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query("select n from User n where n.username = ?1")
  Optional<User> findByUsername(String username);

}
