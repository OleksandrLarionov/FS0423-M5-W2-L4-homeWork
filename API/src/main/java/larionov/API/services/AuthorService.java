package larionov.API.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import larionov.API.exceptions.BadRequestException;
import larionov.API.exceptions.NotFoundException;
import larionov.API.payloads.authors.NewAuthorDTO;
import larionov.API.repositories.AuthorDAO;
import larionov.API.entities.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class AuthorService {
    @Autowired
    private AuthorDAO authorDAO;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<Author> getAuthors(int page, int size, String orderBy) {
//        return authorDAO.findAll();
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
//        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,orderBy));

        return authorDAO.findAll(pageable);
    }


    public Author saveAuthor(NewAuthorDTO body) {
        authorDAO.findByEmail(body.email()).ifPresent(author -> {
            throw new BadRequestException("L'email è gia in uso " + author.getEmail());
        });
        Author newAuthor = new Author();
        newAuthor.setAvatar("htpps://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        newAuthor.setDataDiNascita(body.dataDiNascita());
        newAuthor.setName(body.name());
        newAuthor.setSurname(body.surname());
        newAuthor.setEmail(body.email());
        return authorDAO.save(newAuthor);
    }

    public Author findById(Long id) {
        return authorDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void delete(Long id) {
        Author found = this.findById(id);
        authorDAO.delete(found);

    }

    public Author findByIdAndUpdate(Long id, Author body) {
        Author found = this.findById(id);
        found.setId(id);
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setEmail(body.getEmail());
        found.setDataDiNascita(body.getDataDiNascita());
        return authorDAO.save(found);
    }


    private LocalDate getRandomDate() {
        Random random = new Random();
        LocalDate startDate = LocalDate.of(1990, 1, 1);
        LocalDate endDate = LocalDate.now();

        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = startEpochDay + random.nextInt((int) (endEpochDay - startEpochDay + 1));

        return LocalDate.ofEpochDay(randomEpochDay);
    }

    public String uploadPicture(MultipartFile file, Long authorId) throws IOException {

        String url = (String) cloudinaryUploader.uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap())
                .get("url");
        Author found = this.findById(authorId);
        found.setAvatar(url);
        authorDAO.save(found);
        return "Avatar è stato aggiornato";
    }
}
