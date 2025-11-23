package me.blackwater.blogsai2.infrastructure.handler.section;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.CreateHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.CreateSectionRequest;
import me.blackwater.blogsai2.domain.exception.SectionAlreadyExistException;
import me.blackwater.blogsai2.domain.model.Section;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.repository.SectionRepository;
import me.blackwater.blogsai2.infrastructure.handler.user.GetUserByUserNameHandler;

@Handler(handlerType = HandlerType.CREATE, name = "Create section",transactional = true)
@RequiredArgsConstructor
public class CreateSectionHandler implements CreateHandler<Section, CreateSectionRequest> {

    private final SectionRepository sectionRepository;
    private final GetUserByUserNameHandler getUserByUserNameHandler;

    @Override
    public Section execute(CreateSectionRequest dto) {
        sectionRepository.findByTitle(dto.title()).ifPresent(section -> {
            throw new SectionAlreadyExistException("Section with title " + dto.title() + " already exists");
        });
        final User user = getUserByUserNameHandler.execute(dto.creator());

        return sectionRepository.save(new Section(dto.title(), dto.description(), user,dto.type()));
    }

    @Override
    public boolean trace() {
        return true;
    }
}
