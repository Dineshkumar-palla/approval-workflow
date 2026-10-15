package com.example.approval.architecture;
import com.tngtech.archunit.core.importer.ImportOption;import com.tngtech.archunit.junit.AnalyzeClasses;import com.tngtech.archunit.junit.ArchTest;import com.tngtech.archunit.lang.ArchRule;import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
@AnalyzeClasses(packages="com.example.approval", importOptions=ImportOption.DoNotIncludeTests.class) class ArchitectureTest {
@ArchTest static final ArchRule domain_is_independent=noClasses().that().resideInAPackage("..domain..").should().dependOnClassesThat().resideInAnyPackage("..application..","..adapter..","..bootstrap..");
@ArchTest static final ArchRule domain_has_no_framework_dependencies=noClasses().that().resideInAPackage("..domain..").should().dependOnClassesThat().resideInAnyPackage("org.springframework..","jakarta.persistence..","javax.persistence..","org.hibernate..","java.sql..");
@ArchTest static final ArchRule application_does_not_depend_on_adapters=noClasses().that().resideInAPackage("..application..").should().dependOnClassesThat().resideInAPackage("..adapter..");
@ArchTest static final ArchRule adapters_do_not_depend_on_bootstrap=noClasses().that().resideInAPackage("..adapter..").should().dependOnClassesThat().resideInAPackage("..bootstrap.."); }
