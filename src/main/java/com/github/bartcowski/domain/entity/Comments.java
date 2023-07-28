package com.github.bartcowski.domain.entity;

import java.util.List;
import java.util.stream.Collectors;

public record Comments(List<Comment> comments) {

    List<Comment> findContainingPhrase(String phrase) {
        return comments.stream()
                .filter(comment -> comment.containsPhrase(phrase))
                .collect(Collectors.toList());
    }
}
