package mirkoabozzi.U5S6L4.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "blog_posts")
public class BlogPost {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String category;
    private String title;
    private String cover;
    private String content;
    @Column(name = "reading_time")
    private int readingTime;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
