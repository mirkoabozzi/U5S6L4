package mirkoabozzi.U5S6L4.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BlogPostsDTO(
        @NotNull(message = "ID is required")
        UUID authorId,
        @NotNull(message = "Category is required")
        String category,
        @NotNull(message = "Title is required")
        String title,
        @NotNull(message = "Content is required")
        String content,
        @NotNull(message = "Reading time is required")
        int readingTime
) {
}
