package com.sulima.dao;

import com.sulima.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    boolean existsByLoginAndPass(String login, String pass);

    boolean existsByLogin(String login);

    Users findByLogin(String login);
}
