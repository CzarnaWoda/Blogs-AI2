package me.blackwater.blogsai2.application.web.controller;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.api.util.TimeUtil;
import me.blackwater.blogsai2.application.dto.SectionDto;
import me.blackwater.blogsai2.application.mapper.SectionDtoMapper;
import me.blackwater.blogsai2.application.web.request.CreateSectionRequest;
import me.blackwater.blogsai2.application.web.request.GetSectionByTypeRequest;
import me.blackwater.blogsai2.application.web.request.PageRequest;
import me.blackwater.blogsai2.application.web.request.UpdateSectionRequest;
import me.blackwater.blogsai2.domain.model.Section;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.infrastructure.handler.section.*;
import me.blackwater.blogsai2.infrastructure.handler.user.GetUserByEmailHandler;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/section")
@RequiredArgsConstructor
class SectionControllerImpl implements SectionController{

    private final GetSectionsByTypeHandler getSectionsByTypeHandler;
    private final GetSectionByTitleHandler getSectionByTitleHandler;
    private final GetSectionPageHandler getSectionPageHandler;
    private final CreateSectionHandler createSectionHandler;
    private final SectionDtoMapper sectionDtoMapper;
    private final GetSectionByIdHandler getSectionByIdHandler;
    private final GetUserByEmailHandler getUserByEmailHandler;
    private final UpdateSectionByIdHandler updateSectionByIdHandler;

    @Override
    @GetMapping("/sections/type/{type}")
    public ResponseEntity<HttpResponse> sectionsByType(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @PathVariable String type) {
       final Page<Section> sections = getSectionsByTypeHandler.execute(new GetSectionByTypeRequest(page,size,type));

       return ResponseEntity.status(OK).body(HttpResponse.builder()
                       .statusCode(OK.value())
                       .httpStatus(OK)
                       .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                       .message("Sections by type")
                       .reason("Sections request")
                       .data(Map.of("sections", sections.getContent().stream().filter(Section::isActive).map(sectionDtoMapper::toDto).collect(Collectors.toSet()), "totalElements", sections.getTotalElements(), "totalPages", sections.getTotalPages()))
               .build());
    }

    @Override
    @GetMapping("/section/title/{title}")
    public ResponseEntity<HttpResponse> sectionByTitle(@PathVariable String title) {

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .statusCode(OK.value())
                .httpStatus(OK)
                .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                .message("Section by title")
                .reason("Section by title request")
                .data(Map.of("section", sectionDtoMapper.toDto(getSectionByTitleHandler.execute(title))))
                .build());
    }

    @Override
    @GetMapping("/sections")
    public ResponseEntity<HttpResponse> sections(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        final Page<Section> sections = getSectionPageHandler.execute(new PageRequest(page,size));

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .statusCode(OK.value())
                .httpStatus(OK)
                .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                .message("Sections page")
                .reason("Sections page request")
                .data(Map.of("sections", sections.getContent().stream().filter(Section::isActive).map(sectionDtoMapper::toDto).collect(Collectors.toSet()), "totalElements", sections.getTotalElements(), "totalPages", sections.getTotalPages()))
                .build());
    }

    @Override
    @PostMapping()
    public ResponseEntity<HttpResponse> createSections(@RequestBody CreateSectionRequest request, Authentication authentication) {
        final User user = getUserByEmailHandler.execute(authentication.getName());

        final SectionDto sectionDto = sectionDtoMapper.toDto(createSectionHandler.execute(new CreateSectionRequest(user.getUserName(),request.title(),request.description(),request.type())));

        return ResponseEntity.status(CREATED).body(HttpResponse.builder()
                .statusCode(CREATED.value())
                .httpStatus(CREATED)
                .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                .message("Section created")
                .reason("Section create request")
                .data(Map.of("section", sectionDto))
                .build());
    }

    @Override
    @PutMapping()
    public ResponseEntity<HttpResponse> updateSection(@RequestBody UpdateSectionRequest request) {
        final Section section = updateSectionByIdHandler.execute(request);


        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .statusCode(OK.value())
                .httpStatus(OK)
                .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                .message("Section updated")
                .reason("Section update request")
                .data(Map.of("section", sectionDtoMapper.toDto(section)))
                .build());
    }

    @Override
    @GetMapping("/section/id/{id}")
    public ResponseEntity<HttpResponse> sectionById(@PathVariable long id) {
        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .statusCode(OK.value())
                .httpStatus(OK)
                .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                .message("Section by id")
                .reason("Section by id request")
                .data(Map.of("section", sectionDtoMapper.toDto(getSectionByIdHandler.execute(id))))
                .build());
    }
}
