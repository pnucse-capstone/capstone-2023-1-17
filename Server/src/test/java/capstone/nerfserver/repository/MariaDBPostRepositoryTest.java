package capstone.nerfserver.repository;

import capstone.nerfserver.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class MariaDBPostRepositoryTest {
/*
    @Autowired
    MariaDBPostRepository mariaDBPostRepository;

    @AfterEach
    void afterEach(){
        mariaDBPostRepository.clearStore();
    }

    @Test
    void save() {
        Post post = new Post(1L, "title", "content", 1000L, 3L);
        mariaDBPostRepository.save(post);
        System.out.println("post id: " + post.getId());
        Post result = mariaDBPostRepository.findById(post.getId()).get();
        System.out.println("title: " + result.getTitle());
        System.out.println("content: " + result.getContent());
        System.out.println("price: " + result.getPrice());
        System.out.println("numberOfImages: " + result.getNumberOfImages());

        Assertions.assertThat(post.getId()).isEqualTo(result.getId());
    }

    @Test
    void update(){
        Post post = new Post(1L, "title", "content", 1000L, 3L);
        mariaDBPostRepository.save(post);
        post.setState("done");
        mariaDBPostRepository.update(post);
        Post result = mariaDBPostRepository.findById(post.getId()).get();
        Assertions.assertThat(post.getState()).isEqualTo(result.getState());
        System.out.println("state: " + result.getState());
    }
    @Test
    void findByUserId(){
        Post post1 = new Post(1L, "title1", "content1", 1000L, 1L);
        mariaDBPostRepository.save(post1);
        Post post2 = new Post(1L, "title2", "content2", 2000L, 2L);
        mariaDBPostRepository.save(post2);

        //when
        List<Post> list = mariaDBPostRepository.findByUserId(post1.getUserId());

        //then
        Post result = list.get(0);
        System.out.println("post2 id: " + post2.getId());
        System.out.println("result id: " + result.getId());
        Assertions.assertThat(post2.getId()).isEqualTo(result.getId());
        Assertions.assertThat(list.size()).isEqualTo(2);
    }


    @Test
    void findById(){
        //given
        Post post = new Post(1L, "title", "content", 1000L, 3L);
        mariaDBPostRepository.save(post);

        //when
        Post result = mariaDBPostRepository.findById(post.getId()).get();

        //then
        System.out.println("post id: " + post.getId());
        System.out.println("result id: " + result.getId());
        Assertions.assertThat(post.getId()).isEqualTo(result.getId());
    }

    @Test
    void findAll() {
        //given
        Post post1 = new Post(1L, "title1", "content1", 1000L, 1L);
        mariaDBPostRepository.save(post1);
        Post post2 = new Post(2L, "title2", "content2", 2000L, 2L);
        mariaDBPostRepository.save(post2);

        //when
        List<Post> list = mariaDBPostRepository.findAll();

        //then
        Post result = list.get(0);
        System.out.println("post2 id: " + post2.getId());
        System.out.println("result id: " + result.getId());
        Assertions.assertThat(post2.getId()).isEqualTo(result.getId());
        Assertions.assertThat(list.size()).isEqualTo(2);
    }
 */
}