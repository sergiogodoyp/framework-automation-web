package web.hook;

import driver.DriverManager;
import io.cucumber.java.*;



import java.util.logging.Logger;

public class Hook {
    private static final Logger LOGGER = Logger.getLogger(Hook.class.getName());
    private static boolean resultadosPublicados = false;


    @Before
    public void configuracionInicial(Scenario scenario) {
        String navegador = System.getProperty("browser", "chrome");
        DriverManager.setBrowser(navegador);
        DriverManager.getDriver();
        LOGGER.info("Iniciando escenario: " + scenario.getName());
    }

    @After
    public void liberarRecursos(Scenario scenario) {
        DriverManager.quitDriver();
        String estado = scenario.isFailed() ? "❌ FAILED" : "✅ PASSED";
        LOGGER.info("Finalizado escenario: " + scenario.getName() + " - Estado: " + estado);
    }
}