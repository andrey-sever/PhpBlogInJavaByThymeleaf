package com.sulima.dao;

import com.sulima.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {

    List<Comments> findAllByArticleId(Integer id);

    int countByArticleId(Integer id);
}
