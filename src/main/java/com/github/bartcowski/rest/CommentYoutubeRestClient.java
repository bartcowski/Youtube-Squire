package com.github.bartcowski.rest;

import com.github.bartcowski.authorization.AuthorizationSupplier;
import com.github.bartcowski.domain.entity.Comment;
import com.github.bartcowski.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentYoutubeRestClient extends AbstractYoutubeRestClient {

    private final AuthorizationSupplier authorizationSupplier;

    public List<Comment> getCommentsOfVideo(String videoId) {
        RestRequestBuilder requestBuilder = new RestRequestBuilder(BASE_URL)
                .GET()
                .path("/commentThreads")
                .params(buildParams(videoId))
                .authorize(authorizationSupplier);

        return sendRequest(requestBuilder, CommentResponseDTO.class).items.stream()
                .map(dto -> dto.snippet.topLevelComment.snippet)
                .map(TopLevelCommentSnippetDTO::toDomain)
                .collect(Collectors.toList());
    }

    private Map<String, String> buildParams(String videoId) {
        return Map.of(
                "part", "snippet",
                "textFormat", "plainText",
                "videoId", videoId,
                "maxResults", "5" //TODO: just for testing, paging needed
        );
    }

    private static class CommentResponseDTO {
        List<CommentResponseItemDTO> items;
    }

    private static class CommentResponseItemDTO {
        CommentSnippetDTO snippet;
    }

    private static class CommentSnippetDTO {
        TopLevelCommentDTO topLevelComment;
    }

    private static class TopLevelCommentDTO {
        TopLevelCommentSnippetDTO snippet;
    }

    private static class TopLevelCommentSnippetDTO {
        String authorDisplayName;
        String textDisplay;
        String likeCount;
        //String textOriginal;
        //String publishedAt;
        //String updatedAt;

        Comment toDomain() {
            return new Comment(
                    new User("unknown", authorDisplayName),
                    textDisplay,
                    Integer.parseInt(likeCount)
            );
        }
    }

}
