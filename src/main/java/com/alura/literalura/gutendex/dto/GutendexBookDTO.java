package com.alura.literalura.gutendex.dto;

import java.util.List;

public record GutendexBookDTO(
        Integer id,
        String title,
        List<GutendexAuthorDTO> authors,
        List<String> languages,
        Integer download_count
) {
}
