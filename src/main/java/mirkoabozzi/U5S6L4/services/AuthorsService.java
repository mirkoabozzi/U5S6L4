package mirkoabozzi.U5S6L4.services;


import mirkoabozzi.U5S6L4.dto.NewAuthorDTO;
import mirkoabozzi.U5S6L4.entities.Author;
import mirkoabozzi.U5S6L4.exceptions.BadRequestException;
import mirkoabozzi.U5S6L4.exceptions.NotFoundException;
import mirkoabozzi.U5S6L4.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorsService {
    @Autowired
    private AuthorsRepository authorsRepository;

    //GET ALL
    public Page<Author> findAll(int page, int size, String shortBy) {
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(shortBy));
        return this.authorsRepository.findAll(pageable);
    }

    // GET BY ID
    public Author findById(UUID id) {
        return this.authorsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    //POST
    public Author saveAuthor(NewAuthorDTO payload) {
        if (authorsRepository.existsByEmail(payload.email()))
            throw new BadRequestException("Email " + payload.email() + " già presente nel DB");
        Author newAuthor = new Author(payload.name(), payload.surname(), payload.email(), payload.birthDate(), "https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname());
        return this.authorsRepository.save(newAuthor);

    }

    //PUT
    public Author findByIdAndUpdate(UUID id, Author newAuthor) {
        Author found = this.findById(id);
        found.setName(newAuthor.getName());
        found.setSurname(newAuthor.getSurname());
        found.setEmail(newAuthor.getEmail());
        found.setBirthDate(newAuthor.getBirthDate());
        found.setAvatar("https://ui-avatars.com/api/?name=" + newAuthor.getName() + "+" + newAuthor.getSurname());
        this.authorsRepository.save(found);
        return found;
    }

    //DELETE
    public void findByIdAndDelete(UUID id) {
        Author found = this.findById(id);
        this.authorsRepository.delete(found);
    }

}
