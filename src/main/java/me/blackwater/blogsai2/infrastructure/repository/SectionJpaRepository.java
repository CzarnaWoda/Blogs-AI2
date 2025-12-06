package me.blackwater.blogsai2.infrastructure.repository;

import me.blackwater.blogsai2.domain.model.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface SectionJpaRepository extends JpaRepository<Section,Long> {

    Optional<Section> findById(long id);

    Optional<Section> findByTitle(String title);

    Page<Section> findByType(Pageable pageable, String type);

    @EntityGraph("section.creator")
    Page<Section> findAllByActiveTrue(Pageable pageable);
}
