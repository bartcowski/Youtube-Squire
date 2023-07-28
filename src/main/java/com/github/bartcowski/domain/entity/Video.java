package com.github.bartcowski.domain.entity;

import java.time.LocalDate;

public record Video(
        String title,
        int viewsCount,
        LocalDate uploadDate) {
}
