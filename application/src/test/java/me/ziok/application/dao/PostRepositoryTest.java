package me.ziok.application.dao;

import me.ziok.application.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void crud() {
        Post post = new Post();
        post.setPostName("postname");
        post.setContent("kkkkk");
        postRepository.save(post);

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

}