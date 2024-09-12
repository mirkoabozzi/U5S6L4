package mirkoabozzi.U5S6L4.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import mirkoabozzi.U5S6L4.dto.BlogPostsDTO;
import mirkoabozzi.U5S6L4.entities.Author;
import mirkoabozzi.U5S6L4.entities.BlogPost;
import mirkoabozzi.U5S6L4.exceptions.NotFoundException;
import mirkoabozzi.U5S6L4.repositories.BlogPostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class BlogPostsService {
    @Autowired
    Cloudinary cloudinary;
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
    public BlogPost saveBlogPost(BlogPostsDTO payload) {
        BlogPost bp = new BlogPost();
        Author authorFound = this.authorsService.findById(payload.authorId());
        bp.setAuthor(authorFound);
        bp.setCategory(payload.category());
        bp.setContent(payload.content());
        bp.setReadingTime(payload.readingTime());
        bp.setTitle(payload.title());
        bp.setCover("http://localhost:8080/" + payload.title());
        return this.blogPostsRepository.save(bp);
    }

    //PUT
    public BlogPost findByIdAndUpdate(UUID id, BlogPostsDTO newBlogPost) {
        BlogPost found = this.findById(id);
        found.setCategory(newBlogPost.category());
        found.setTitle(newBlogPost.title());
        found.setContent(newBlogPost.content());
        found.setReadingTime(newBlogPost.readingTime());
        return this.blogPostsRepository.save(found);
    }

    //DELETE
    public void findBiIdAndDelete(UUID id) {
        BlogPost found = this.findById(id);
        this.blogPostsRepository.delete(found);
    }

    // IMG UPLOAD
    public void imgUpload(MultipartFile file, UUID id) throws IOException {
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        BlogPost bp = this.findById(id);
        bp.setCover(url);
        this.blogPostsRepository.save(bp);
    }
}
