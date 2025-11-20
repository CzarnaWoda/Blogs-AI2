package me.blackwater.blogsai2.infrastructure.handler.section;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.domain.exception.SectionNotFoundException;
import me.blackwater.blogsai2.domain.model.Section;
import me.blackwater.blogsai2.domain.repository.SectionRepository;

@Handler(handlerType = HandlerType.GET, name = "Get section by title")
@RequiredArgsConstructor
public class GetSectionByTitleHandler implements GetHandler<Section,String> {

    private final SectionRepository sectionRepository;
    @Override
    public Section execute(String title) {
        return sectionRepository.findByTitle(title).orElseThrow(() -> new SectionNotFoundException("Section by title has not been found"));
    }
}
