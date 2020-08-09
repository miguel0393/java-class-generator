package com.example.java_class_generator;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Future;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

@Service
public class FileWriterAdapter implements FileWriter {

    @Override
    public Mono<String> writeFile(String name, String template) {
        return Mono.fromCallable(() -> {
            final Path path = Paths.get("/home/mbo/Documentos/Projects/source/java-class-generator/generated_classes/"
                    + StringUtils.capitalize(name) + ".java");

            try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, WRITE, CREATE)) {
                final ByteBuffer buffer = ByteBuffer.wrap(template.getBytes());
                final Future<Integer> write = fileChannel.write(buffer, 0);
                buffer.clear();
                write.get();

            } catch (Exception e) {
                System.err.println(e.toString());
            }

            return name;
        });
    }
}
