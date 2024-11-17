package org.springproject.coffeeshopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springproject.coffeeshopapp.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
