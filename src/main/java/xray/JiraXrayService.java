package xray;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class JiraXrayService {
    private static final Logger LOGGER = Logger.getLogger(JiraXrayService.class.getName());
    private static JiraXrayService instance;
    private String xrayToken;
    private Properties properties;
    private ObjectMapper objectMapper;

    private JiraXrayService() {
        this.objectMapper = new ObjectMapper();
        loadProperties();
        authenticateXray();
    }

    public static synchronized JiraXrayService getSingletonInstance() {
        if (instance == null) {
            instance = new JiraXrayService();
        }
        return instance;
    }

    private void loadProperties() {
        try {
            properties = new Properties();
            String configPath = "src/test/resources/configuracion/config.properties";
            properties.load(new FileInputStream(configPath));
            LOGGER.info("Configuración cargada correctamente");
        } catch (IOException e) {
            LOGGER.severe("Error al cargar el archivo de configuración: " + e.getMessage());
        }
    }

    private void authenticateXray() {
        try {
            String clientId = properties.getProperty("xray.clientId");
            String clientSecret = properties.getProperty("xray.clientSecret");

            String requestBody = String.format(
                    "{\"client_id\": \"%s\", \"client_secret\": \"%s\"}",
                    clientId, clientSecret
            );

            Response response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post("https://xray.cloud.getxray.app/api/v2/authenticate");

            if (response.getStatusCode() == 200) {
                xrayToken = response.getBody().asString().replace("\"", "");
                LOGGER.info("Autenticación con Xray exitosa");
            } else {
                LOGGER.severe("Error en autenticación Xray: " + response.getBody().asString());
            }
        } catch (Exception e) {
            LOGGER.severe("Error durante la autenticación con Xray: " + e.getMessage());
        }
    }

    public void importResultsInJiraXraySaas() {
        try {
            File cucumberJsonFile = new File("target/build/cucumber.json");

            if (!cucumberJsonFile.exists()) {
                LOGGER.warning("Archivo de resultados no encontrado: " + cucumberJsonFile.getAbsolutePath());
                return;
            }

            JsonNode cucumberJson = objectMapper.readTree(cucumberJsonFile);
            String projectKey = properties.getProperty("xray.projectKey", "SCRUM");

            // Agregar información del proyecto al JSON de resultados
            String importPayload = objectMapper.writeValueAsString(cucumberJson);

            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + xrayToken)
                    .header("Content-Type", "application/json")
                    .body(importPayload)
                    .post("https://xray.cloud.getxray.app/api/v2/import/execution/cucumber");

            if (response.getStatusCode() == 200) {
                JsonNode responseJson = objectMapper.readTree(response.getBody().asString());
                String testExecKey = responseJson.get("key").asText();
                LOGGER.info("✅ Resultados publicados exitosamente en Xray: " + testExecKey);

                // Opcional: Actualizar el JSON con la clave de ejecución
                updateJsonWithTestExecKey(cucumberJsonFile, testExecKey);

            } else {
                LOGGER.severe("❌ Error al publicar resultados en Xray: " + response.getStatusCode());
                LOGGER.severe("Respuesta: " + response.getBody().asString());
            }

        } catch (Exception e) {
            LOGGER.severe("❌ Error en importResultsInJiraXraySaas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateJsonWithTestExecKey(File jsonFile, String testExecKey) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonFile);
            ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode)
                    .put("xray_test_exec_key", testExecKey);

            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(jsonFile, rootNode);

        } catch (Exception e) {
            LOGGER.warning("No se pudo actualizar el JSON con la clave de ejecución: " + e.getMessage());
        }
    }

}