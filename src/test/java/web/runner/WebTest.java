package web.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import xray.JiraXrayService;


import java.util.logging.Logger;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "html:target/build/cucumber-html-report",
                "pretty:target/build/cucumber-pretty.txt",
                "json:target/build/cucumber.json", // Este debe ser el último plugin
                "junit:target/build/cucumber-junit.xml"
        },
        features = {"src/test/resources/features/"},
        stepNotifications = true,
        publish = true, // Cambiar a false para evitar publicación automática de Cucumber
        glue = {"web.stepdefinitions", "web.hook"},
        tags = "@SCRUM-8"
)
public class WebTest {

    private static final Logger LOGGER = Logger.getLogger(WebTest.class.getName());

    @AfterClass
    public static void publicarResultadosEnJira() {
        try {
            JiraXrayService jiraService = JiraXrayService.getSingletonInstance();
            jiraService.importResultsInJiraXraySaas();
            LOGGER.info("✅ Publicación a Xray completada exitosamente");
        } catch (Exception e) {
            LOGGER.severe("❌ Error durante la publicación a Xray: " + e.getMessage());
            e.printStackTrace();
        }
    }
}