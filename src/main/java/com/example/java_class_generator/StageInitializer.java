package com.example.java_class_generator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<FormApplication.StageReadyEvent> {

    @Value("classpath:/chart.fxml")
    private Resource chartResource;

    private final ApplicationContext applicationContext;

    private final String applicationTitle;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
                            ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(FormApplication.StageReadyEvent event) {

        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(chartResource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            final Parent parent = fxmlLoader.load();

            final Stage stage = event.getStage();
            stage.setScene(new Scene(parent));
            stage.setTitle(applicationTitle);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }
}
