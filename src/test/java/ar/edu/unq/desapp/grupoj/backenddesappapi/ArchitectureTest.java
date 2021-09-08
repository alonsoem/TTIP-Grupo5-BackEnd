package ar.edu.unq.desapp.grupoj.backenddesappapi;

import com.tngtech.archunit.PublicAPI;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import static com.tngtech.archunit.PublicAPI.Usage.ACCESS;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

public class ArchitectureTest {
    @Test
    public void service_classes_should_have_service_annotation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupoj.backenddesappapi");

        ArchRule rule = classes().that().areAnnotatedWith(Service.class).or()
                .haveNameMatching(".*Service").should().resideInAPackage("..service..");

        rule.check(importedClasses);
    }

    @Test
    public void controller_classes_should_have_controller_annotation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupoj.backenddesappapi");

        ArchRule rule = classes().that().areAnnotatedWith(Controller.class).or()
                .haveNameMatching(".*Controller").should().resideInAPackage("..webservices..");
        rule.check(importedClasses);
    }

    @Test
    public void model_classes_not_depending_on_service_or_controller() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupoj.backenddesappapi.model");
        ArchRule rule = classes().that().resideInAPackage("..model..")
                .should().onlyHaveDependentClassesThat().resideInAnyPackage("..model..");
    }

    @Test
    public void no_classes_should_access_systemOut() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupoj.backenddesappapi");
        @PublicAPI(usage=ACCESS)
        ArchRule rule =  NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
        rule.check(importedClasses);
    }
}