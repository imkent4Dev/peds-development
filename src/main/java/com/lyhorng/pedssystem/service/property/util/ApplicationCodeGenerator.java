package com.lyhorng.pedssystem.service.property.util;

import com.lyhorng.pedssystem.repository.property.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class ApplicationCodeGenerator {

    private final PropertyRepository propertyRepository;

    public String generateUniqueApplicationCode() {
        String code;
        do {
            code = generateRandom(11);
        } while (propertyRepository.existsByApplicationCode(code));
        return code;
    }

    private String generateRandom(int length) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) sb.append(rnd.nextInt(10));
        return sb.toString();
    }
}
