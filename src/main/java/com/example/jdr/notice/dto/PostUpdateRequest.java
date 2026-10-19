package com.example.jdr.notice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}