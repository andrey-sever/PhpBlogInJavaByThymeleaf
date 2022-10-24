package com.sulima.data;

import com.sulima.model.Articles;
import com.sulima.model.ArticlesCountComments;
import com.sulima.model.Comments;
import com.sulima.dao.ArticlesRepository;
import com.sulima.dao.CommentsRepository;

import com.sulima.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticlesService {

    private final ArticlesRepository articlesRepository;
    private final CommentsRepository commentsRepository;

    public void saveArticle(Articles article, HttpServletRequest request) {
        article.setDate(new Date().getTime());
        article.setAuthor(CookieFactory.getCookieByName(request, "login").getValue());
        articlesRepository.save(article);
    }

    public List<ArticlesCountComments> getListArticles() {
//        return articlesRepository.findAllByOrderByDateDesc();
        List<Articles> articlesList = articlesRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
        ArrayList<ArticlesCountComments> countComments = new ArrayList<>();
        for (Articles article : articlesList) {
            ArticlesCountComments articlesCountComments = new ArticlesCountComments();
            articlesCountComments.setId(article.getId());
            articlesCountComments.setTitle(article.getTitle());
            articlesCountComments.setIntro(article.getIntro());
            articlesCountComments.setText(article.getText());
            articlesCountComments.setDate(article.getDate());
            articlesCountComments.setAuthor(article.getAuthor());
            articlesCountComments.setCount(getCountCommentsByArticleId(article.getId()));
            countComments.add(articlesCountComments);
        }
        return countComments;
    }

    public Articles getArticleById(Integer id) {
        return articlesRepository.findById(id).get();
    }

    public void saveComment(Comments comment) {
        commentsRepository.save(comment);
    }

    public List<Comments> getListComments(Integer id) {
        return commentsRepository.findAllByArticleId(id);
    }

    public String getDateInHuman(Long dateIn) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy HH:mm");
        return dateFormat.format(dateIn);
    }

    public Comments fillDataFromNewComment(Integer id, Users userCookie) {
        Comments newComment = new Comments();
        if (userCookie == null) return newComment;

        newComment.setArticleId(id);
        newComment.setLogin(userCookie.getLogin());
        return newComment;
    }

    public int getCountCommentsByArticleId(Integer id) {
        return commentsRepository.countByArticleId(id);
    }
}
