package capstone.nerfserver.repository;

import capstone.nerfserver.domain.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MariaDBPostRepository extends PostRepository{
    void save(Post post);
    void update(Post post);
    List<Post> findByUserId(Long userId);
    Optional<Post> findById(Long id);
    List<Post> findAll();
    void clearStore();
}