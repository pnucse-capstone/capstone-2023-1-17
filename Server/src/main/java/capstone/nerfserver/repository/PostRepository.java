package capstone.nerfserver.repository;

import capstone.nerfserver.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    void save(Post post);
    void update(Post post);
    List<Post> findByUserId(Long userId);
    Optional<Post> findById(Long id);
    List<Post> findAll();
}
