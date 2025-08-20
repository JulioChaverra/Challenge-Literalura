package com.alura.literalura.gutendex.dto;

import java.util.List;

public record GutendexResponse(
        Integer count,
        String next,
        String previous,
        List<GutendexBookDTO> results
) {
}
