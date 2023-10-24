package capstone.nerfserver.service;

import capstone.nerfserver.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class PostServiceTest {
/*
    @Autowired
    PostService service;

    @AfterEach
    void afterEach(){
        service.clearStore();
    }

    @Test
    void upload() {
        //given
        Post post = new Post();

        //when
        service.upload(post);

        //then
        Post result = service.findPost(post.getId()).get();
        Assertions.assertThat(post).isEqualTo(result);
    }

    @Test
    void finishNerf() {
        //given
        Post post = new Post();
        post.setState("waiting");
        service.upload(post);

        //when
        service.finishNerf(post.getId());

        //then
        Assertions.assertThat(service.findPost(post.getId()).get().getState()).isEqualTo("done");
    }

    @Test
    void allPosts() {
        //given
        Post post1 = new Post();
        service.upload(post1);
        Post post2 = new Post();
        service.upload(post2);

        //when
        List<Post> list = service.allPosts();

        //then
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void findPost() {
        //given
        Post post = new Post();

        //when
        service.upload(post);

        //then
        Post result = service.findPost(post.getId()).get();
        Assertions.assertThat(post).isEqualTo(result);
    }

    @Test
    void findMesh() {
    }
 */
}