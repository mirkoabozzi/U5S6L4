package mirkoabozzi.U5S6L4.repositories;

import mirkoabozzi.U5S6L4.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlogPostsRepository extends JpaRepository<BlogPost, UUID> {
}
