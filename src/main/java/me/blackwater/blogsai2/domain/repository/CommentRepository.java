package me.blackwater.blogsai2.domain.repository;

import me.blackwater.blogsai2.domain.model.Comment;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(long id);
    Page<Comment> findByArticleId(int page, int size, long articleId);
    Page<Comment> findByAuthorId(int page, int size, long authorId);
    Page<Comment> findByArticleIdWithAuthor(int page, int size, long articleId);
    Comment save(Comment comment);

}
