package com.github.bartcowski.domain.entity;

public record Channel(
        String id,
        String name,
        int viewsCount,
        int videosCount,
        int subsCount) {
}
