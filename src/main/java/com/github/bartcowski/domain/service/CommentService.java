package com.github.bartcowski.domain.service;

import com.github.bartcowski.domain.entity.Comment;
import com.github.bartcowski.domain.entity.User;
import com.github.bartcowski.infrastructure.rest.CommentYoutubeRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentYoutubeRestClient commentYoutubeRestClient;

    private final Random random = new Random();

    public List<Comment> findAllComments(String resourceId) {
        return commentYoutubeRestClient.findAllComments(resourceId);
    }

    public List<Comment> findCommentsWithPhrase(String resourceId, String phrase) {
        return commentYoutubeRestClient.findAllComments(resourceId)
                .stream()
                .filter(comment -> comment.containsPhrase(phrase))
                .collect(Collectors.toList());
    }

    public Comment findRandomCommentWithPhrase(String resourceId, String phrase) {
        List<Comment> comments = findCommentsWithPhrase(resourceId, phrase);
        int randomIndex = random.nextInt(comments.size());
        return comments.get(randomIndex);
    }

    public Comment findRandomCommentWithPhraseAndDeduplicatedAuthors(String resourceId, String phrase) {
        Map<User, Comment> authorToCommentMap = new HashMap<>();
        findCommentsWithPhrase(resourceId, phrase)
                .forEach(comment -> authorToCommentMap.putIfAbsent(comment.author(), comment));
        List<Comment> commentsWithDeduplicatedAuthors = new ArrayList<>(authorToCommentMap.values());
        int randomIndex = random.nextInt(commentsWithDeduplicatedAuthors.size());
        return commentsWithDeduplicatedAuthors.get(randomIndex);
    }

    public int countCommentsWithPhrase(String resourceId, String phrase) {
        return findCommentsWithPhrase(resourceId, phrase).size();
    }

    public List<Comment> findTopLikedComments(String resourceId, int topCount) {
        return findAllComments(resourceId).stream()
                .sorted(Comparator.comparing(Comment::likeCount).reversed())
                .limit(topCount)
                .collect(Collectors.toList());
    }

}
