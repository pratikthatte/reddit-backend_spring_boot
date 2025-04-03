package com.project.redditclone.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.redditclone.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByUserName(String userName);
}
