package com.example.java_class_generator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log
@Component
@RequiredArgsConstructor
public class ChartController {

    @FXML
    public TextField txtRootNameClass;
    private final GenerateClassController generateClassController;

    public Mono<Void> generateClasses(ActionEvent actionEvent) {

        generateClassController.generateClass(GenerateClassRequest.builder()
                .rootClassName(txtRootNameClass.getText())
                .build())
                .subscribe();

        return Mono.empty();

//        return Mono.just(txtRootNameClass.getText())
//                .map(rootClassName -> GenerateClassRequest.builder()
//                        .rootClassName(rootClassName)
//                        .build())
//                .doOnNext(request -> log.log(Level.INFO, "Generate class request: {0}", request))
//                .flatMap(generateClassController::generateClass);
    }
}
