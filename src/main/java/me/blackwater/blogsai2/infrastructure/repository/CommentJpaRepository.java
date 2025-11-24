package me.blackwater.blogsai2.infrastructure.repository;

import me.blackwater.blogsai2.domain.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByAuthorId(long authorId, Pageable pageable);

    @EntityGraph("Comment.author")
    Page<Comment> findByArticleId(long articleId, Pageable pageable);
}
