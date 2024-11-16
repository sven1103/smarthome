package edu.stevenfil.smart.smartapp;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Theme(value = "smarthome", variant = Lumo.DARK)
public class SmartappApplication extends SpringBootServletInitializer implements
    AppShellConfigurator {

  public static void main(String[] args) {
    SpringApplication.run(SmartappApplication.class, args);
  }

}
