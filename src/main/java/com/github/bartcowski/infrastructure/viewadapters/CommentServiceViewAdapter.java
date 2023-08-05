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

    public List<CommentViewDTO> findAllComments(String resourceId) {
        List<Comment> comments = commentService.findAllComments(resourceId);
        return convertIntoViewEntities(comments);
    }

    public List<CommentViewDTO> findCommentsWithPhrase(String resourceId, String phrase) {
        List<Comment> comments = commentService.findCommentsWithPhrase(resourceId, phrase);
        return convertIntoViewEntities(comments);
    }

    public CommentViewDTO findRandomCommentWithPhrase(String resourceId, String phrase) {
        Comment comment = commentService.findRandomCommentWithPhrase(resourceId, phrase);
        return convertIntoViewEntity(comment);
    }

    public CommentViewDTO findRandomCommentWithPhraseAndDeduplicatedAuthors(String resourceId, String phrase) {
        Comment comment = commentService.findRandomCommentWithPhraseAndDeduplicatedAuthors(resourceId, phrase);
        return convertIntoViewEntity(comment);
    }

    public int countCommentsWithPhrase(String resourceId, String phrase) {
        return commentService.countCommentsWithPhrase(resourceId, phrase);
    }

    public List<CommentViewDTO> findTopLikedComments(String resourceId, int topCount) {
        List<Comment> comments = commentService.findTopLikedComments(resourceId, topCount);
        return convertIntoViewEntities(comments);
    }

    private List<CommentViewDTO> convertIntoViewEntities(List<Comment> comments) {
        return comments.stream()
                .map(CommentViewDTO::of)
                .collect(Collectors.toList());
    }

    private CommentViewDTO convertIntoViewEntity(Comment comment) {
        return CommentViewDTO.of(comment);
    }

}
