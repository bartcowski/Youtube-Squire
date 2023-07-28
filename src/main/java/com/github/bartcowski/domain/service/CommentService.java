package com.github.bartcowski.domain.service;

import com.github.bartcowski.domain.entity.Comment;
import com.github.bartcowski.rest.CommentYoutubeRestClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentYoutubeRestClient commentYoutubeRestClient;

    public List<Comment> findComments(String videoId) {
        return commentYoutubeRestClient.getCommentsOfVideo(videoId);
    }

}
