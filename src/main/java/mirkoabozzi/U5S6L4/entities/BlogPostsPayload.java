package mirkoabozzi.U5S6L4.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostsPayload {
    private UUID authorId;
    private String category;
    private String title;
    private String cover;
    private String content;
    private int readingTime;
}
