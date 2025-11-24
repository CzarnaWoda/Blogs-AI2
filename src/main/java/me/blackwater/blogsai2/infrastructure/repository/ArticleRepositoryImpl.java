package me.blackwater.blogsai2.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.domain.exception.ObjectRequirementsException;
import me.blackwater.blogsai2.domain.model.Article;
import me.blackwater.blogsai2.domain.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class ArticleRepositoryImpl implements ArticleRepository {

    private final ArticleJpaRepository articleJpaRepository;

    @Override
    public Optional<Article> findById(long id) {
        return articleJpaRepository.findById(id);
    }

    @Override
    public Optional<Article> findByTitle(String title) {
        return articleJpaRepository.findByTitle(title);
    }

    @Override
    public List<Article> findByAuthorId(long id) {
        return articleJpaRepository.findByAuthorId(id);
    }

    @Override
    public List<Article> findBySectionId(long id) {
        return articleJpaRepository.findBySectionId(id);
    }

    @Override
    public Page<Article> findPageableSortedOrdered(int page, int size, String sortDirection, String orderBy) {
        try {
            final Sort.Direction direction = Sort.Direction.fromString(sortDirection);

            return articleJpaRepository.findAll(PageRequest.of(page, size, direction, orderBy));
        }catch (IllegalArgumentException e) {
            throw new ObjectRequirementsException("Sort direction has to be 'ASC' or 'DESC'");
        }
    }

    @Override
    public Article save(Article article) {
        return articleJpaRepository.save(article);
    }
}
