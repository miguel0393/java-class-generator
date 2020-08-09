package com.example.java_class_generator;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GenerateClassController {

    private final FileWriter fileWriter;

    public Mono<Void> generateClass(GenerateClassRequest generateClassRequest) {

        final String file = "/home/mbo/Documentos/Projects/source/java-class-generator/json.json";
        final Path path = Paths.get(file);

        return Flux.using(() -> Files.lines(path), Flux::fromStream, BaseStream::close)
                .collect(Collectors.joining())
                .map(JSONObject::new)
                .map(JSONObject::toMap)
                .map(x -> buildFile(generateClassRequest.getRootClassName(), x))
                .switchIfEmpty(Mono.error(new RuntimeException("Empty JSON")))
                .doOnError(System.err::println)
                .then();
    }

    private Map<String, Object> buildFile(String className, Map<String, Object> jsonMap) {

        className = StringUtils.capitalize(className);

        // Se escriben las clases hijo
        for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
            if (entry.getValue().getClass().equals(HashMap.class)) {
                buildFile(entry.getKey(), ((Map<String, Object>) entry.getValue()));
            }
        }

        // Se escribe la clase principal
        final String template = buildJavaClassTemplate(className, jsonMap);
        fileWriter.writeFile(className, template)
                .subscribe();

        return jsonMap;
    }

    private String buildJavaClassTemplate(String name, Map<String, Object> fields) {
        final StringBuilder template = new StringBuilder("\n\npublic class " + name + " {\n");

        fields.forEach((key, value) -> {

            String tClass = key;
            if (!value.getClass().equals(HashMap.class)) {
                final String[] classAndPackages = value.getClass().getName().split("\\.");
                tClass = classAndPackages[classAndPackages.length - 1];
            }

            template.append("\n\tprivate ")
                    .append(StringUtils.capitalize(tClass))
                    .append(" ")
                    .append(key)
                    .append(";");
        });

        template.append("\n}");
        return template.toString();
    }
}
