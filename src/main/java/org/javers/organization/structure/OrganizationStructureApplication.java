package org.javers.organization.structure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class OrganizationStructureApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrganizationStructureApplication.class, args);
    }
}
