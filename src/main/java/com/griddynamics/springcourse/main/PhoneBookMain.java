package com.griddynamics.springcourse.main;

import com.griddynamics.springcourse.entity.PhoneBookFormatter;
import com.griddynamics.springcourse.service.PhoneBook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Scanner;

/**
 * PhoneBook entry point
 */
@SpringBootApplication(scanBasePackages = "com.griddynamics.springcourse")
public class PhoneBookMain {

    public static void main(String[] args) {
        // Run the Spring Boot application, which will start the embedded Tomcat server.
        ApplicationContext context = SpringApplication.run(PhoneBookMain.class, args);

        // Load the XML configuration into the existing context
        GenericXmlApplicationContext xmlContext = new GenericXmlApplicationContext();
        xmlContext.load("classpath:application-config.xml");
        xmlContext.setParent(context);
        xmlContext.refresh();

        Scanner sc = new Scanner(System.in);
        sc.useDelimiter(System.getProperty("line.separator"));

        PhoneBook phoneBook = xmlContext.getBean("phoneBook", PhoneBook.class);
        PhoneBookFormatter renderer = (PhoneBookFormatter) xmlContext.getBean("phoneBookFormatter");

        renderer.info("type 'exit' to quit.");
        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (line.equals("exit")) {
                renderer.info("Have a good day...");
                break;
            }
        }
    }
}
