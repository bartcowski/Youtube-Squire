package com.github.bartcowski.infrastructure.viewadapters;

import com.github.bartcowski.domain.entity.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentViewDTO implements ViewEntity {

    String author;

    String text;

    int likesCount;

    public static CommentViewDTO of(Comment comment) {
        return new CommentViewDTO(
                comment.author().name(),
                comment.text(),
                comment.likeCount());
    }

    @Override
    public String asViewFormattedString() {
        return "Author: " + author +
                "\n" + text +
                "\nLikes: " + likesCount +
                "\n";
    }
}
