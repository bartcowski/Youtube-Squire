package com.github.bartcowski.domain.service

import com.github.bartcowski.domain.entity.Comment
import com.github.bartcowski.domain.entity.User

class TestCommentProvider implements CommentProvider {

    @Override
    List<Comment> findAllComments(String resourceId) {
        def user1 = new User("user1")
        def user2 = new User("user2")
        def user3 = new User("user3")

        return [
                new Comment(user1, "__hello!", 10),
                new Comment(user2, "text, text, and more text", 99),
                new Comment(user3, "qwertyuiop", 9_123),
                new Comment(user1, "text", 0),
                new Comment(user2, "hello world", 2),
                new Comment(user3, "a s d f g h i j k l", 5),
                new Comment(user1, "123___xxx", 772),
        ]
    }
}
