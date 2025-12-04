package me.blackwater.blogsai2.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.domain.model.Comment;
import me.blackwater.blogsai2.domain.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class CommentRepositoryImpl implements CommentRepository {
    private final CommentJpaRepository commentJpaRepository;
    @Override
    public Optional<Comment> findById(long id) {
        return commentJpaRepository.findById(id);
    }

    @Override
    public Page<Comment> findByArticleId(int page, int size, long articleId) {
        return commentJpaRepository.findByArticleIdAndDisabled(articleId,false, PageRequest.of(page, size));
    }

    @Override
    public Page<Comment> findByAuthorId(int page, int size, long authorId) {
        return commentJpaRepository.findByAuthorId(authorId, PageRequest.of(page, size));
    }

    @Override
    public Page<Comment> findByArticleIdWithAuthor(int page, int size, long articleId) {
        return commentJpaRepository.findByArticleIdAndDisabled(articleId,false, PageRequest.of(page, size, Sort.Direction.DESC,"createdAt"));
    }

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(comment);
    }
}
