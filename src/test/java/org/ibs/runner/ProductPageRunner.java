package org.ibs.runner;

import org.junit.platform.suite.api.*;
import static io.cucumber.core.options.Constants.*;

/**
 * Класс для запуска тестов Cucumber с использованием JUnit.
 * Определяет параметры конфигурации для запуска тестов, такие как фильтрация тегов,
 * путь к файлам с фичами, пакет с шагами и плагин для Allure.
 */

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameters({
        @ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@all"),
        @ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/features"),
        @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.ibs.steps"),
        @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
})
public class ProductPageRunner {
}