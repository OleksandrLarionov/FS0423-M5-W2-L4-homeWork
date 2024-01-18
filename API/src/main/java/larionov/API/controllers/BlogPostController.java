package larionov.API.controllers;

import larionov.API.entities.Author;
import larionov.API.entities.BlogPost;
import larionov.API.payloads.PayloadBlogPost;
import larionov.API.services.AuthorService;
import larionov.API.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogposts")
public class BlogPostController {

    @Autowired
    BlogPostService blogPostService;

    @Autowired
    AuthorService authorService;

    @GetMapping
    public List<BlogPost> getBlogPosts(@RequestParam(required = false) String category) {
        if (category != null) return blogPostService.filterByCategory(category);
        return blogPostService.getBlogPosts();
    }

    @PostMapping
    public BlogPost saveBlogpost(@RequestBody PayloadBlogPost payload) {
        Long authorId = payload.getAuthorId();
        Author author = authorService.findById(authorId);

        BlogPost blog = new BlogPost();
        blog.setCategoria(payload.getCategoria());
        blog.setTitolo(payload.getTitolo());
        blog.setCover(payload.getCover());
        blog.setContenuto(payload.getContenuto());
        blog.setTempoDiLettura(payload.getTempoDiLettura());

        blog.setAuthor(author);

        return blogPostService.saveBlogPost(blog);
    }

    @GetMapping("/{id}")
    public BlogPost findById(@PathVariable Long id) {
        return blogPostService.findById(id);

    }

    @PutMapping("/{id}")
    public BlogPost findByIdAndUpdate(@PathVariable Long id,
                                      @RequestBody BlogPost body,
                                      @RequestBody Author author) {
        return blogPostService.findByIdAndUpdate(id, body, author);
    }

    @DeleteMapping("/{id}")
    public void findByIdAndDelete(@PathVariable Long id) {
        blogPostService.delete(id);
    }
}
