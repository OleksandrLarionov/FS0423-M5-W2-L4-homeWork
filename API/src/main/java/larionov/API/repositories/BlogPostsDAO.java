package larionov.API.repositories;

import larionov.API.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostsDAO extends JpaRepository<BlogPost, Long> {
}
