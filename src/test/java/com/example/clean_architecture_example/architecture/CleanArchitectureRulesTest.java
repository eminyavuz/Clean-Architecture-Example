package com.example.clean_architecture_example.architecture;


import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.example.clean_architecture_example", importOptions = ImportOption.DoNotIncludeTests.class)
public class CleanArchitectureRulesTest {
    @ArchTest
    static final ArchRule domain_should_not_depend_on_outer_layers=
            noClasses()
                    .that().resideInAnyPackage("..domain..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage("..application..","..adapter..","..infrastructure..","..config..");

    @ArchTest
    static final ArchRule application_should_not_depend_on_infrastructure_or_adapter=
            noClasses()
                    .that().resideInAnyPackage("..application..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage("..adapter..","..infrastructure");

    @ArchTest
    static final  ArchRule domain_should_be_framework_independent=
            noClasses()
                    .that().resideInAnyPackage("..domain..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage("org.springframework..","jakarta.persistence..");

}
