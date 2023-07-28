package com.github.bartcowski.infrastructure.viewadapters;

import com.github.bartcowski.domain.entity.Comment;
import com.github.bartcowski.domain.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceViewAdapter {

    private final CommentService commentService;

    public List<CommentViewDTO> findComments(String videoId) {
        List<Comment> comments = commentService.findComments(videoId);
        return convertIntoViewEntities(comments);
    }

    private List<CommentViewDTO> convertIntoViewEntities(List<Comment> comments) {
        return comments.stream()
                .map(CommentViewDTO::of)
                .collect(Collectors.toList());
    }

}
