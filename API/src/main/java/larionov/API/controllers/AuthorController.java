package larionov.API.controllers;

import com.cloudinary.Cloudinary;
import larionov.API.config.MailGunSender;
import larionov.API.entities.Author;
import larionov.API.exceptions.BadRequestException;
import larionov.API.payloads.authors.NewAuthorDTO;
import larionov.API.payloads.authors.NewAuthorResponseDTO;
import larionov.API.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    AuthorService authorService;
    @Autowired
    MailGunSender mailGunSender;

    @GetMapping
    public Page<Author> getAuthor(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy) {
        return authorService.getAuthors(page, size, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewAuthorResponseDTO saveAuthor(@RequestBody @Validated NewAuthorDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
            Author newAuthor = authorService.saveAuthor(body);

            mailGunSender.sendRegistrationMail(newAuthor.getEmail());
            return new NewAuthorResponseDTO(newAuthor.getId());
        }
    }

    @GetMapping("/{id}")
    public Author findById(@PathVariable Long id) {
        return authorService.findById(id);

    }

    @PutMapping("/{id}/avatar")
    public Author findByIdAndUpdate(@PathVariable Long id, @RequestBody Author body) {
        return authorService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long id) {
        authorService.delete(id);
    }

    @PostMapping("/{authorId}/upload")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable(required = true) Long authorId) throws IOException {
        return authorService.uploadPicture(file,authorId);
    }
}
