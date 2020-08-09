package com.example.java_class_generator;

import reactor.core.publisher.Mono;

public interface FileWriter {

    Mono<String> writeFile(String name, String template);
}
