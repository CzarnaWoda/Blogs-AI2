package me.blackwater.blogsai2.infrastructure.handler.section;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.GetSectionByTypeRequest;
import me.blackwater.blogsai2.domain.model.Section;
import me.blackwater.blogsai2.domain.repository.SectionRepository;
import org.springframework.data.domain.Page;

import java.time.Duration;
import java.time.Instant;

@Handler(handlerType = HandlerType.GET, name = "Sections by type", transactional = true)
@RequiredArgsConstructor
public class GetSectionsByTypeHandler implements GetHandler<Page<Section>, GetSectionByTypeRequest> {

    private final SectionRepository sectionRepository;

    @Override
    public Page<Section> execute(GetSectionByTypeRequest getSectionByTypeRequest) {
        final Page<Section> sections = sectionRepository.findByType(getSectionByTypeRequest.type(),getSectionByTypeRequest.page(), getSectionByTypeRequest.size());


        sections.getContent().forEach(section -> {
            section.addView();

            if(Duration.between(section.getUpdatedAt(), Instant.now()).compareTo(Duration.ofMinutes(5)) >= 0) {
                section.update();

                sectionRepository.save(section);
            }
        });

        return sections;
    }
}
