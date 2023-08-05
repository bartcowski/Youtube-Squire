package com.github.bartcowski.domain.entity;

public record Comment(
        User author,
        String text,
        int likeCount) {

    public boolean containsPhrase(String phrase) {
        return text.toLowerCase().contains(phrase.toLowerCase());
    }
}
