package me.blackwater.blogsai2.infrastructure.repository;

import me.blackwater.blogsai2.domain.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ArticleJpaRepository extends JpaRepository<Article,Long> {

    Optional<Article> findByTitle(String title);
    List<Article> findByAuthorId(long authorId);
    List<Article> findBySectionId(long sectionId);

    @EntityGraph("article.author")
    Page<Article> findAllByBlockedFalse(Pageable pageable);

    int countAllByAuthorId(long authorId);
}
