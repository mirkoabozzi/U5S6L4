package mirkoabozzi.U5S6L4.services;

import mirkoabozzi.U5S6L4.entities.Author;
import mirkoabozzi.U5S6L4.entities.BlogPost;
import mirkoabozzi.U5S6L4.entities.BlogPostsPayload;
import mirkoabozzi.U5S6L4.exceptions.NotFoundException;
import mirkoabozzi.U5S6L4.repositories.BlogPostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlogPostsService {
    @Autowired
    private BlogPostsRepository blogPostsRepository;
    @Autowired
    private AuthorsService authorsService;

    //GET ALL
    public Page<BlogPost> findAll(int page, int size, String shortBy) {
        if (page > 100) page = 100;
        return this.blogPostsRepository.findAll(PageRequest.of(page, size, Sort.by(shortBy)));
    }

    //GET BLOGPOST
    public BlogPost findById(UUID id) {
        return this.blogPostsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    // POST
    public BlogPost saveBlogPost(BlogPostsPayload payload) {
        BlogPost bp = new BlogPost();
        Author authorFound = this.authorsService.findById(payload.getAuthorId());
        bp.setAuthor(authorFound);
        bp.setCategory(payload.getCategory());
        bp.setContent(payload.getContent());
        bp.setReadingTime(payload.getReadingTime());
        bp.setTitle(payload.getTitle());
        bp.setCover("http://localhost:8080/" + payload.getTitle());
        this.blogPostsRepository.save(bp);
        return bp;
    }

    //PUT
    public BlogPost findByIdAndUpdate(UUID id, BlogPost newBlogPost) {
        BlogPost found = this.findById(id);
        found.setCategory(newBlogPost.getCategory());
        found.setTitle(newBlogPost.getTitle());
        found.setContent(newBlogPost.getContent());
        found.setReadingTime(newBlogPost.getReadingTime());
        this.blogPostsRepository.save(found);
        return found;
    }

    //DELETE
    public void findBiIdAndDelete(UUID id) {
        BlogPost found = this.findById(id);
        this.blogPostsRepository.delete(found);
    }

}
