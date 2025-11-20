package me.blackwater.blogsai2.infrastructure.handler.section;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.PageRequest;
import me.blackwater.blogsai2.domain.model.Section;
import me.blackwater.blogsai2.domain.repository.SectionRepository;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
@Handler(handlerType = HandlerType.GET, name = "Get section page")
public class GetSectionPageHandler implements GetHandler<Page<Section>, PageRequest> {

    private final SectionRepository sectionRepository;
    @Override
    public Page<Section> execute(PageRequest pageRequest) {
        return sectionRepository.findAll(pageRequest.page(), pageRequest.size());
    }
}
