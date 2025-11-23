package me.blackwater.blogsai2.infrastructure.handler.section;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.domain.exception.SectionNotFoundException;
import me.blackwater.blogsai2.domain.model.Section;
import me.blackwater.blogsai2.domain.repository.SectionRepository;

import java.time.Duration;
import java.time.Instant;

@Handler(handlerType = HandlerType.GET, name = "Get Section by id")
@RequiredArgsConstructor
public class GetSectionByIdHandler implements GetHandler<Section,Long> {

    private final SectionRepository sectionRepository;
    @Override
    public Section execute(Long id) {
        final Section section = sectionRepository.findById(id).orElseThrow(() -> new SectionNotFoundException("Section has not been found"));

        section.addView();

        if(Duration.between(section.getUpdatedAt(),Instant.now()).compareTo(Duration.ofMinutes(5)) >= 0) {
            section.update();

            sectionRepository.save(section);
        }
        return section;
    }
}
