package com.github.bartcowski.domain.service;

import com.github.bartcowski.domain.entity.Comment;

import java.util.List;

public interface CommentProvider {

    List<Comment> findAllComments(String resourceId);

}
