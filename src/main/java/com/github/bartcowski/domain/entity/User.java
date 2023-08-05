package com.github.bartcowski.domain.entity;

public record User(
        String id,
        String name) {

    public User(String name) {
        this("N/A", name);
    }
}
