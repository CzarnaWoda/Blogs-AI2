package me.blackwater.blogsai2.infrastructure.handler.section;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.UpdateHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.UpdateSectionRequest;
import me.blackwater.blogsai2.domain.model.Section;
import me.blackwater.blogsai2.domain.repository.SectionRepository;

@Handler(name = "Update by id", handlerType = HandlerType.GET, transactional = true)
@RequiredArgsConstructor
public class UpdateSectionByIdHandler implements UpdateHandler<Section, UpdateSectionRequest> {
    private final GetSectionByIdHandler getSectionByIdHandler;
    private final SectionRepository sectionRepository;
    @Override
    public Section execute(UpdateSectionRequest dto) {
        final Section section = getSectionByIdHandler.execute(dto.id());

        section.update(dto.title(),dto.description(),dto.type());

        return sectionRepository.save(section);
    }
}
