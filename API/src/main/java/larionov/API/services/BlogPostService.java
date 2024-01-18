package larionov.API.services;

import larionov.API.entities.Author;
import larionov.API.entities.BlogPost;
import larionov.API.exceptions.NotFoundException;
import larionov.API.repositories.AuthorDAO;
import larionov.API.repositories.BlogPostsDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BlogPostService {
    @Autowired
    private BlogPostsDAO blogPostDao;
    @Autowired
    private AuthorDAO authorDAO;

    public List<BlogPost> getBlogPosts() {
        return blogPostDao.findAll();
    }

    public BlogPost saveBlogPost(BlogPost body) {

        return blogPostDao.save(body);
    }

    public BlogPost findById(Long id) {
        return blogPostDao.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void delete(Long id) {
        BlogPost found = this.findById(id);
        blogPostDao.delete(found);
    }

    public BlogPost findByIdAndUpdate(Long id, BlogPost body, Author author) {
        BlogPost found = this.findById(id);
        found.setId(id);
        found.setCategoria(body.getCategoria());
        found.setTitolo(body.getTitolo());
        found.setAuthor(body.getAuthor());
        return found;

    }

    public List<BlogPost> filterByCategory(String categoria) {
        List<BlogPost> categoryList = this.getBlogPosts().stream()
                .filter(blogPost -> blogPost.getCategoria().equals(categoria))
                .collect(Collectors.toList());

        return categoryList;
    }


}

