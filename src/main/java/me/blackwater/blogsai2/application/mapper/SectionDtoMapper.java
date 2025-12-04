package me.blackwater.blogsai2.application.mapper;


import me.blackwater.blogsai2.api.util.TimeUtil;
import me.blackwater.blogsai2.application.dto.SectionDto;
import me.blackwater.blogsai2.domain.model.Section;
import org.springframework.stereotype.Component;

@Component
public class SectionDtoMapper {

    public SectionDto toDto(Section section) {
        return new SectionDto(section.getId(),section.getCreator().getUserName(),section.getCreator().getEmail().value(),section.getTitle(),section.getDescription(),section.getViews(), TimeUtil.getTimeInStandardFormat(section.getCreatedAt()), section.getType());
    }
}
