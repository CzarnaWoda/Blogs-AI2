package me.blackwater.blogsai2.domain.repository;

import me.blackwater.blogsai2.domain.model.Article;
import me.blackwater.blogsai2.domain.model.Comment;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Optional<Article> findById(long id);
    Optional<Article> findByTitle(String title);
    List<Article> findByAuthorId(long id);
    List<Article> findBySectionId(long id);
    Page<Article> findPageableSortedOrdered(int page, int size, String sortDirection, String orderBy);
    Article save(Article article);
    int countByAuthorId(long id);
}
