package com.example.jdr.notice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Long authorId;
}