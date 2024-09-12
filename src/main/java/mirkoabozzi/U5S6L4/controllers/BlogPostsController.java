package mirkoabozzi.U5S6L4.controllers;

import mirkoabozzi.U5S6L4.dto.BlogPostsDTO;
import mirkoabozzi.U5S6L4.entities.BlogPost;
import mirkoabozzi.U5S6L4.exceptions.BadRequestException;
import mirkoabozzi.U5S6L4.services.BlogPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blogPosts")
public class BlogPostsController {

    @Autowired
    BlogPostsService blogPostsService;

    //GET ALL
    @GetMapping
    private Page<BlogPost> getAllBlogPosts(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "readingTime") String sortBy) {
        return blogPostsService.findAll(page, size, sortBy);
    }

    //GET BY ID
    @GetMapping("/{id}")
    private BlogPost findById(@PathVariable UUID id) {
        return blogPostsService.findById(id);
    }

    //POST
    @PostMapping
    private BlogPost createBlogPosts(@RequestBody @Validated BlogPostsDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(" ."));
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return blogPostsService.saveBlogPost(body);
        }
    }

    //PUT
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    private BlogPost findByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated BlogPostsDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(" ."));
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return blogPostsService.findByIdAndUpdate(id, body);
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findByIdAndDelete(@PathVariable UUID id) {
        blogPostsService.findBiIdAndDelete(id);
    }

    //POST COVER
    @PostMapping("/cover/{id}")
    public void coverUpload(@RequestParam("cover") MultipartFile img, @PathVariable UUID id) throws IOException {
        this.blogPostsService.imgUpload(img, id);
    }

}
