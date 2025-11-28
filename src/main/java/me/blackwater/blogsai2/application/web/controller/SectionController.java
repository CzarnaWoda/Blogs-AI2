package me.blackwater.blogsai2.application.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.application.web.request.CreateSectionRequest;
import me.blackwater.blogsai2.application.web.request.UpdateSectionRequest;
import org.springframework.http.ResponseEntity;

@Tag(name = "Section Management", description = "API endpoints for managing blog sections")
interface SectionController {

    @Operation(
            summary = "Get sections by type with pagination",
            description = "Retrieves a paginated list of active sections filtered by type"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved sections",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "timeStamp": "2025-11-23T10:30:00",
                                        "httpStatus": "OK",
                                        "statusCode": 200,
                                        "reason": "Sections request",
                                        "message": "Sections by type",
                                        "data": {
                                            "sections": [
                                                {
                                                    "id": 1,
                                                    "title": "Introduction to Java",
                                                    "description": "A comprehensive guide to Java programming",
                                                    "views": 150,
                                                    "createdAt": "2025-11-20T08:00:00",
                                                    "type": "tutorial"
                                                }
                                            ],
                                            "totalElements": 10,
                                            "totalPages": 2
                                        }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    ResponseEntity<HttpResponse> sectionsByType(
            @Parameter(description = "Page number (zero-based)", example = "0")
            int page,

            @Parameter(description = "Number of items per page", example = "5")
            int size,

            @Parameter(description = "Section type to filter by", example = "tutorial", required = true)
            String type
    );

    @Operation(
            summary = "Get section by title",
            description = "Retrieves a single section by its unique title"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved section",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "timeStamp": "2025-11-23T10:30:00",
                                        "httpStatus": "OK",
                                        "statusCode": 200,
                                        "reason": "Section by title request",
                                        "message": "Section by title",
                                        "data": {
                                            "section": {
                                                "id": 1,
                                                "title": "Introduction to Java",
                                                "description": "A comprehensive guide to Java programming",
                                                "views": 150,
                                                "createdAt": "2025-11-20T08:00:00",
                                                "type": "tutorial"
                                            }
                                        }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Section not found")
    })
    ResponseEntity<HttpResponse> sectionByTitle(
            @Parameter(description = "Title of the section to retrieve", example = "Introduction to Java", required = true)
            String title
    );

    @Operation(
            summary = "Get all sections with pagination",
            description = "Retrieves a paginated list of all active sections"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved sections",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "timeStamp": "2025-11-23T10:30:00",
                                        "httpStatus": "OK",
                                        "statusCode": 200,
                                        "reason": "Sections page request",
                                        "message": "Sections page",
                                        "data": {
                                            "sections": [
                                                {
                                                    "id": 1,
                                                    "title": "Introduction to Java",
                                                    "description": "A comprehensive guide to Java programming",
                                                    "views": 150,
                                                    "createdAt": "2025-11-20T08:00:00",
                                                    "type": "tutorial"
                                                },
                                                {
                                                    "id": 2,
                                                    "title": "Advanced Spring Boot",
                                                    "description": "Deep dive into Spring Boot features",
                                                    "views": 220,
                                                    "createdAt": "2025-11-21T09:15:00",
                                                    "type": "guide"
                                                }
                                            ],
                                            "totalElements": 25,
                                            "totalPages": 5
                                        }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    ResponseEntity<HttpResponse> sections(
            @Parameter(description = "Page number (zero-based)", example = "0")
            int page,

            @Parameter(description = "Number of items per page", example = "5")
            int size
    );

    @Operation(
            summary = "Create a new section",
            description = "Creates a new blog section with the provided details"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Section created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "timeStamp": "2025-11-23T10:30:00",
                                        "httpStatus": "CREATED",
                                        "statusCode": 201,
                                        "reason": "Section create request",
                                        "message": "Section created",
                                        "data": {
                                            "section": {
                                                "id": 3,
                                                "title": "Microservices Architecture",
                                                "description": "Building scalable microservices",
                                                "views": 0,
                                                "createdAt": "2025-11-23T10:30:00",
                                                "type": "architecture"
                                            }
                                        }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    ResponseEntity<HttpResponse> createSections(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Section creation details",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateSectionRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "creator": "John Doe",
                                        "title": "Microservices Architecture",
                                        "description": "Building scalable microservices with Spring Boot and Docker",
                                        "type": "architecture"
                                    }
                                    """
                            )
                    )
            )
            CreateSectionRequest request
    );

    @Operation(
            summary = "Update an existing section",
            description = "Updates an existing section with new details"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Section updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "timeStamp": "2025-11-23T10:30:00",
                                        "httpStatus": "OK",
                                        "statusCode": 200,
                                        "reason": "Section update request",
                                        "message": "Section updated",
                                        "data": {
                                            "section": {
                                                "id": 1,
                                                "title": "Introduction to Java - Updated",
                                                "description": "An updated comprehensive guide to Java programming",
                                                "views": 150,
                                                "createdAt": "2025-11-20T08:00:00",
                                                "type": "tutorial"
                                            }
                                        }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Section not found")
    })
    ResponseEntity<HttpResponse> updateSection(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Section update details",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UpdateSectionRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "id": 1,
                                        "title": "Introduction to Java - Updated",
                                        "description": "An updated comprehensive guide to Java programming",
                                        "type": "tutorial"
                                    }
                                    """
                            )
                    )
            )
            UpdateSectionRequest request
    );

    @Operation(
            summary = "Get section by ID",
            description = "Retrieves a single section by its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved section",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "timeStamp": "2025-11-23T10:30:00",
                                        "httpStatus": "OK",
                                        "statusCode": 200,
                                        "reason": "Section by id request",
                                        "message": "Section by id",
                                        "data": {
                                            "section": {
                                                "id": 1,
                                                "title": "Introduction to Java",
                                                "description": "A comprehensive guide to Java programming",
                                                "views": 150,
                                                "createdAt": "2025-11-20T08:00:00",
                                                "type": "tutorial"
                                            }
                                        }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Section not found")
    })
    ResponseEntity<HttpResponse> sectionById(
            @Parameter(description = "ID of the section to retrieve", example = "1", required = true)
            long id
    );
}