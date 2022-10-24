package com.sulima.dao;

import com.sulima.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Integer> {

    List<Articles> findAllByOrderByDateDesc();
}
