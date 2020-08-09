package com.example.java_class_generator;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenerateClassRequest {

    private final String rootClassName;

}
