package me.blackwater.blogsai2.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.domain.model.Section;
import me.blackwater.blogsai2.domain.repository.SectionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class SectionRepositoryImpl implements SectionRepository {

    private final SectionJpaRepository sectionJpaRepository;
    @Override
    public Optional<Section> findById(long id) {
        return sectionJpaRepository.findById(id);
    }

    @Override
    public Section save(Section section) {
        return sectionJpaRepository.save(section);
    }

    @Override
    public Optional<Section> findByTitle(String title) {
        return sectionJpaRepository.findByTitle(title);
    }


    @Override
    public Page<Section> findAll(int page, int size) {
        return sectionJpaRepository.findAll(PageRequest.of(page, size, Sort.Direction.ASC,"createdAt"));
    }

    @Override
    public Page<Section> findByType(String type, int page, int size) {
        return sectionJpaRepository.findByType(PageRequest.of(page,size, Sort.Direction.ASC,"createdAt"), type);
    }
}
