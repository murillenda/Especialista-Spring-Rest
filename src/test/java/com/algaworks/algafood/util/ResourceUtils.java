package com.algaworks.algafood.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ResourceUtils {

    private ResourceUtils() {}

    // Método utilizado para Jsons pequenos a médios (~< 500 linhas)
    // E o json Precisa estar identado para funcionar
    public static String getContentFromResourcePath(String resourcePath) {
        StringBuilder json = new StringBuilder();
        try (var stream = Files.lines(Path.of(resourcePath), StandardCharsets.UTF_8)) {
            stream.forEach(json::append);
            return json.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
