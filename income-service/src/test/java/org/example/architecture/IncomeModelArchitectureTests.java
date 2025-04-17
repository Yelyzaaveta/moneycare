package org.example.architecture;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

/*
  @author Orynchuk
  @project micro
  @class IncomeArchitectureTests
  @version 1.0.0
  @since 17.04.2025 - 22.44
*/

class IncomeModelArchitectureTests {

    private JavaClasses classes;

    @BeforeEach
    void setup() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.example");
    }

    @Test
    void shouldFollowLayeredArchitecture() {
        layeredArchitecture()
                .consideringAllDependencies()
                .layer("Controller").definedBy("org.example.controller..")
                .layer("Service").definedBy("org.example.service..")
                .layer("Repository").definedBy("org.example.repository..")

                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Service")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
                .check(classes);
    }

    @Test
    void controllersShouldBeAnnotatedWithRestController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().beAnnotatedWith(RestController.class)
                .check(classes);
    }

    @Test
    void controllerClassesShouldBeNamedProperly() {
        classes()
                .that().resideInAPackage("..controller..")
                .should()
                .haveSimpleNameEndingWith("Controller")
                .check(classes);
    }

    @Test
    void controllersShouldNotDependOnOtherControllers() {
        noClasses()
                .that().resideInAPackage("..controller..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..controller..")
                .because("Controllers cant depend on other controllers")
                .check(classes);
    }

    @Test
    void controllersShouldBePublic() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().bePublic()
                .check(classes);
    }

    @Test
    void anyControllerFieldsShouldNotBeAnnotatedAutowired() {
        noClasses()
                .that().resideInAPackage("..controller..")
                .should()
                .beAnnotatedWith(Autowired.class)
                .check(classes);
    }

    @Test
    void dtoClassesShouldBeNamedProperly() {
        classes()
                .that().resideInAPackage("..dto..")
                .should()
                .haveSimpleNameEndingWith("DTO")
                .check(classes);
    }

    @Test
    void dtoShouldNotBeAnnotatedWithSpringAnnotations() {
        noClasses()
                .that().resideInAPackage("..dto..")
                .should().beAnnotatedWith(javax.persistence.Entity.class)
                .orShould().beAnnotatedWith(org.springframework.stereotype.Service.class)
                .orShould().beAnnotatedWith(org.springframework.stereotype.Repository.class)
                .check(classes);
    }

    @Test
    void dtoFieldsShouldBePrivate() {
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..dto..")
                .should().bePrivate()
                .check(classes);
    }

    @Test
    void dtoShouldNotDependOnModels() {
        noClasses()
                .that().resideInAPackage("..dto..")
                .should().dependOnClassesThat().resideInAPackage("..model..")
                .because("DTOs should be decoupled from internal models")
                .check(classes);
    }

    @Test
    void dtoClassesShouldNotDependOnModel() {
        noClasses()
                .that().resideInAPackage("..dto..")
                .should().dependOnClassesThat().resideInAPackage("..model..")
                .because("DTO must`n depend on internal model")
                .check(classes);
    }

    @Test
    void mapperClassesShouldBeNamedProperly() {
        classes()
                .that().resideInAPackage("..mapper..")
                .should()
                .haveSimpleNameEndingWith("Mapper")
                .check(classes);
    }

    @Test
    void mappersShouldNotDependOnController() {
        noClasses()
                .that().resideInAPackage("..mapper..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..controller..")
                .check(classes);
    }

    @Test
    void mappersShouldNotDependOnService() {
        noClasses()
                .that().resideInAPackage("..mapper..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..service..")
                .check(classes);
    }

    @Test
    void modelFieldsShouldBePrivate() {
        fields()
                .that().areDeclaredInClassesThat()
                .resideInAPackage("..model..")
                .should().notBePublic()
                .because("Fields must be incapsulated")
                .check(classes);
    }

    @Test
    void modelClassesShouldBeNamedProperly() {
        classes()
                .that().resideInAPackage("..model..")
                .should()
                .haveSimpleNameEndingWith("Model")
                .check(classes);
    }

    @Test
    void modelClassesShouldNotDependOnController() {
        noClasses()
                .that().resideInAPackage("..model..")
                .should().dependOnClassesThat().resideInAnyPackage("..controller..")
                .because("Model should not depend on controllers")
                .check(classes);
    }

    @Test
    void modelClassesShouldNotDependOnService() {
        noClasses()
                .that().resideInAPackage("..model..")
                .should().dependOnClassesThat().resideInAnyPackage("..service..")
                .because("Model should not depend on services")
                .check(classes);
    }

    @Test
    void modelClassesShouldNotDependOnDto() {
        noClasses()
                .that().resideInAPackage("..model..")
                .should().dependOnClassesThat().resideInAnyPackage("..dto..")
                .because("Model should not depend on DTOs")
                .check(classes);
    }

    @Test
    void repositoryInterfaceClassesShouldBeNamedProperly() {
        classes()
                .that().resideInAPackage("..repository..")
                .should()
                .haveSimpleNameEndingWith("Repository")
                .check(classes);
    }

    @Test
    void repositoriesShouldBeInterfaces() {
        classes()
                .that().resideInAPackage("..repository..")
                .should()
                .beInterfaces()
                .check(classes);
    }

    @Test
    void repositoriesShouldNotDependOnServices() {
        noClasses()
                .that().resideInAPackage("..repository..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..service..")
                .because("Out of arch rules")
                .check(classes);
    }

    @Test
    void serviceClassesShouldBePublic() {
        classes()
                .that().resideInAPackage("..service..")
                .should().bePublic()
                .check(classes);
    }

    @Test
    void serviceClassesShouldBeNamedProperly() {
        classes()
                .that().resideInAPackage("..service..")
                .should()
                .haveSimpleNameEndingWith("Service")
                .check(classes);
    }

    @Test
    void serviceClassesShouldBeAnnotatedWithService() {
        classes()
                .that().resideInAPackage("..service..")
                .should().beAnnotatedWith(org.springframework.stereotype.Service.class)
                .check(classes);
    }

    @Test
    void servicesShouldNotDependOnControllers() {
        noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat()
                .resideInAPackage("..controller..")
                .check(classes);
    }

}