package com.github.bartcowski.infrastructure.rest;

import com.github.bartcowski.domain.entity.Comment;
import com.github.bartcowski.domain.entity.User;
import com.github.bartcowski.infrastructure.authorization.AuthorizationSupplier;
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

        //TODO: cache K=videoId V=List<Comment> ??? Request heavy even with 100 results per page
        return sendPageableRequest(requestBuilder, CommentResponseDTO.class).stream()
                .map(dto -> dto.items)
                .flatMap(List::stream)
                .map(dto -> dto.snippet.topLevelComment.snippet)
                .map(TopLevelCommentSnippetDTO::toDomain)
                .collect(Collectors.toList());
    }

    private Map<String, String> buildParams(String videoId) {
        return Map.of(
                "part", "snippet",
                "textFormat", "plainText",
                "videoId", videoId,
                "maxResults", "100"
        );
    }

    private static class CommentResponseDTO implements PageableResponse {
        List<CommentResponseItemDTO> items;
        String nextPageToken;

        @Override
        public String getNextPageId() {
            return nextPageToken;
        }
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
