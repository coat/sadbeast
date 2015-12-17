package com.sadbeast.service;

import com.sadbeast.dto.PostDto;
import com.sadbeast.web.beans.PostBean;
import org.dalesbred.Database;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Singleton
public class PostServiceIT {
    private Database db;
    private PostService postService;

    @Test
    public void postShouldSave() {
        PostBean post = new PostBean("hep", "127.0.0.1");

/*        long id = postService.createPost(post);
        assertNotNull(id);

        List<PostDto> posts = postService.findLatestPosts();
        assertEquals(1, posts.size());*/
    }

    @After
    public void tearDown() {
//        db.update("DELETE FROM posts");
    }
}

