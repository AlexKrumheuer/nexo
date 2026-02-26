package com.example.nexo.util;

import java.text.Normalizer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.example.nexo.infra.exception.SlugException;

@Component
public class SlugUtil {
    public static String generateSlug(String title, Predicate<String> slugExists) {
        if(title == null || title.trim().isEmpty()) {
            throw new SlugException("Title for Slug must not be blank or null",HttpStatus.CONFLICT);
        }
        String slug = Normalizer.normalize(title, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        slug = pattern.matcher(slug).replaceAll("");

        slug = slug.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");

        String originalSlug = slug;
        int count = 1;
        while (slugExists.test(slug)) {
            slug = originalSlug + "-" + count;
            count++;
        }

        return slug;
    }
}
