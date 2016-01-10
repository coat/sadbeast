package com.sadbeast.service;

import com.sadbeast.dao.TopicDao;
import com.sadbeast.dto.NewTopicDto;
import com.sadbeast.dto.TopicDto;
import com.sadbeast.util.PostUtil;
import com.sadbeast.web.beans.TopicBean;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static humanize.Humanize.slugify;

@Singleton
public class TopicService {
    private final TopicDao topicDao;
    private final PostService postService;

    @Inject
    public TopicService(final TopicDao topicDao, final PostService postService) {
        this.topicDao = topicDao;
        this.postService = postService;
    }

    public List<TopicDto> getTopicSummaries() {
        return topicDao.getTopicSummaries();
    }

    public TopicDto getTopic(final Long topicId) {
        TopicDto topic = topicDao.getTopic(topicId).orElse(null);
        topic.setPosts(postService.findPostsByTopic(topicId));

        return topic;
    }

    public TopicDto getTopicSummary(final Long topicId) {
        return topicDao.getTopic(topicId).orElse(null);
    }

    public void create(final TopicBean topicBean) {
        NewTopicDto topic = new NewTopicDto();
        topic.setTitle(topicBean.getTitle());
        topic.setHandle(slugify(topicBean.getTitle()));
        topic.setOriginal(topicBean.getContent());
        topic.setContent(PostUtil.process(topicBean.getContent()));
        topic.setUserId(topicBean.getUserId());

        topicDao.create(topic);
    }
}
