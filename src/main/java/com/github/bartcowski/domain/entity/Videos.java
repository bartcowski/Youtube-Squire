package com.github.bartcowski.domain.entity;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public record Videos(List<Video> videos) {

    public List<Video> getTopViewedVideos(int number) {
        return videos.stream()
                .sorted(Comparator.comparingInt(Video::viewsCount))
                .limit(number)
                .collect(Collectors.toList());
    }

    public List<Video> getLatestVideos(int number) {
        return videos.stream()
                .sorted(Comparator.comparing(Video::uploadDate))
                .limit(number)
                .collect(Collectors.toList());
    }

}
