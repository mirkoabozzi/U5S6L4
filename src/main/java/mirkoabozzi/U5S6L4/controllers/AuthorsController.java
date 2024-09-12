package mirkoabozzi.U5S6L4.controllers;

import mirkoabozzi.U5S6L4.dto.NewAuthorDTO;
import mirkoabozzi.U5S6L4.entities.Author;
import mirkoabozzi.U5S6L4.exceptions.BadRequestException;
import mirkoabozzi.U5S6L4.services.AuthorsService;
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
@RequestMapping("/authors")
public class AuthorsController {
    @Autowired
    AuthorsService authorsService;

    // GET ALL
    @GetMapping
    private Page<Author> getAllAuthors(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size,
                                       @RequestParam(defaultValue = "surname") String sortBy) {
        return authorsService.findAll(page, size, sortBy);
    }

    // GET BY ID
    @GetMapping("/{id}")
    private Author findById(@PathVariable UUID id) {
        return authorsService.findById(id);
    }

    //POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Author saveAuthor(@RequestBody @Validated NewAuthorDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(" ."));
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return authorsService.saveAuthor(body);
        }
    }

    //PUT
    @PutMapping("/{id}")
    private Author findByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated NewAuthorDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(" ."));
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return authorsService.findByIdAndUpdate(id, body);
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findByIdAndDelete(@PathVariable UUID id) {
        authorsService.findByIdAndDelete(id);
    }

    //POST IMG
    @PostMapping("/avatar/{id}")
    public void avatarUpload(@RequestParam("avatar") MultipartFile img, @PathVariable UUID id) throws IOException {
        this.authorsService.imgUpload(img, id);
    }
}
