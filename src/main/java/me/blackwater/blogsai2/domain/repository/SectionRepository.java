package me.blackwater.blogsai2.domain.repository;

import me.blackwater.blogsai2.domain.model.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SectionRepository {

    Optional<Section> findById(long id);
    Section save(Section section);
    Optional<Section> findByTitle(String title);
    Optional<Section> findByType(String type);
    Page<Section> findAll(int page, int size);
}
