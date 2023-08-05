package com.github.bartcowski.domain.service

import spock.lang.Specification
import spock.lang.Subject

class CommentServiceTest extends Specification {

    @Subject
    def commentService = new CommentService(new TestCommentProvider())

    def resourceId = "resourceId"

    def "should find comments that contain given phrase"() {
        given:
        def phrase = "hello"

        when:
        def comments = commentService.findCommentsWithPhrase(resourceId, phrase)

        then:
        comments.stream().allMatch(comment -> comment.text().contains(phrase))
    }

    def "should find random comment that contain given phrase"() {
        given:
        def phrase = "text"

        when:
        def comment1 = commentService.findRandomCommentWithPhrase(resourceId, phrase)
        def comment2 = commentService.findRandomCommentWithPhraseAndDeduplicatedAuthors(resourceId, phrase)

        then:
        comment1.text().contains(phrase)
        comment2.text().contains(phrase)
    }

    def "should count comments that contain given phrase"() {
        given:
        def phrase = "text"

        when:
        def count = commentService.countCommentsWithPhrase(resourceId, phrase)

        then:
        count == 2
    }

    def "should find top 2 liked comments"() {
        given:
        def count = 2

        when:
        def comments = commentService.findTopLikedComments(resourceId, count)

        then:
        comments.size() == count
        comments.get(0).likeCount() > comments.get(1).likeCount()
        comments.get(0).likeCount() == 9_123
        comments.get(1).likeCount() == 772
    }
}
