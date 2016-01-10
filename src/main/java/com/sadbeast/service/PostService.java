package com.sadbeast.service;

import com.sadbeast.dao.PostDao;
import com.sadbeast.dto.PostDto;
import com.sadbeast.util.PostUtil;
import com.sadbeast.web.beans.PostBean;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.pegdown.PegDownProcessor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class PostService {
    private static final PegDownProcessor PEG_DOWN = new PegDownProcessor();

    private final PostDao postDao;

    @Inject
    public PostService(final PostDao postDao) {
        this.postDao = postDao;
    }

    public List<PostDto> findLatestPosts() {
        return postDao.findLatestPosts();
    }

    public List<PostDto> findLatestPosts(final Long prevPostId) {
        return postDao.seek(prevPostId);
    }

    public List<PostDto> search(final String query) {
        return postDao.search(query);
    }

    public PostDto random() {
        return postDao.random();
    }

    public long count() {
        return postDao.count();
    }

    public long createPost(PostBean post) {
        post.setContent(PostUtil.process(post.getOriginal()));

        return postDao.createPost(post);
    }

    public List<PostDto> findPostsByTopic(final Long topicId) {
        return postDao.findPostsByTopic(topicId);
    }
}
