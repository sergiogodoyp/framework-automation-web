# Framework de Automatización Web

Este framework es una solución robusta para la automatización de pruebas funcionales de aplicaciones web. Está construido en Java y utiliza el patrón de diseño Page Object Model (POM), lo que facilita la mantenibilidad, escalabilidad y reutilización del código. Permite la integración continua, la trazabilidad de pruebas con Jira Xray y la ejecución automatizada en pipelines CI/CD.

## 🚀 ¿Por qué adoptar este framework?

- **Integración nativa con Jira Xray:** Publica automáticamente los resultados de tus pruebas en Jira Xray Cloud, facilitando la trazabilidad y el reporte de calidad.
- **Ejecución CI/CD con GitHub Actions:** Ejecuta tus pruebas en pipelines de GitHub Actions, permitiendo validaciones automáticas en cada Pull Request o despliegue.
- **Arquitectura escalable y mantenible:** Basado en Java, Maven, Selenium y Cucumber, siguiendo buenas prácticas de automatización.
- **Fácil de usar y extender:** Pensado para que cualquier QA pueda crear, mantener y ejecutar pruebas rápidamente.

---

## 🏗️ Arquetipo del Proyecto

- **Lenguaje:** Java 17
- **Gestor de dependencias:** Maven
- **Frameworks:** Selenium 4, Cucumber 7, JUnit
- **Integraciones:** Jira Xray Cloud, GitHub Actions
- **Patrón de diseño:** Page Object Model (POM)

**Estructura principal:**
```
framework-automation-web/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── web/
│   │           ├── base/          # Clases base para pages, steps y test (soporte POM)
│   │           ├── driver/        # Gestión y configuración de WebDriver
│   │           ├── xray/          # Integración y utilidades para Jira Xray
│   │           └── utils/         # Utilidades generales y helpers
│   └── test/
│       ├── java/
│       │   └── web/
│       │       ├── pages/         # Objetos de página (implementación POM)
│       │       ├── steps/         # Definición de pasos de Cucumber
│       │       ├── runner/        # Runners de pruebas
│       │       ├── hook/          # Hooks de Cucumber para configuraciones y teardown
│       │       └── utils/         # Utilidades específicas de test
│       └── resources/
│           ├── features/          # Archivos .feature de Cucumber
│           └── configuracion/     # Configuración y properties
├── .github/
│   └── workflows/                 # Workflows de GitHub Actions
├── pom.xml                        # Archivo de configuración Maven
└── README.md
```

- **base/**: Contiene clases abstractas y utilidades base para la implementación del patrón Page Object Model (POM).
- **driver/**: Centraliza la configuraci��n y gestión de los navegadores y WebDriver.
- **xray/**: Incluye la lógica para la integración y publicación de resultados en Jira Xray.
- **pages/**: Implementa los objetos de página siguiendo el patrón POM.
- **steps/**: Define los pasos de Cucumber que interactúan con los objetos de página.
- **runner/**: Contiene los runners de pruebas para la ejecución con JUnit y Cucumber.
- **hook/**: Configuraciones y acciones antes/después de la ejecución de escenarios y liberación de recursos.
- **utils/**: Utilidades y helpers reutilizables en todo el framework.

---

## ⚙️ Configuración Inicial

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/sergiogodoyp/framework-automation-web.git
   cd framework-automation-web
   ```

2. **Configura tus credenciales de Xray en** `src/test/resources/configuracion/config.properties`:
   ```properties
   xray.clientId=TU_CLIENT_ID
   xray.clientSecret=TU_CLIENT_SECRET
   xray.projectKey=SCRUM
   ```

3. **Instala las dependencias:**
   ```bash
   mvn clean install
   ```

---

## 🧪 Ejecución de Pruebas Local

Puedes ejecutar todos los tests con:
```bash
mvn clean test -Dtest=web.runner.WebTest
```

Para ejecutar solo escenarios con un tag específico (por ejemplo, `@SCRUM-8`):
```bash
mvn clean test -Dtest=web.runner.WebTest -Dcucumber.filter.tags=@SCRUM-8
```

---

## 🤖 Ejecución Automática en GitHub Actions

El proyecto incluye un workflow listo para usar en `.github/workflows/web-test.yml`.

**Ejecución manual desde GitHub:**

1. Ve a la pestaña **Actions** en tu repositorio.
2. Selecciona el workflow **Selenium Tests - Custom Tags**.
3. Haz clic en **Run workflow** y elige el tag de Cucumber a ejecutar.

---

## 📊 Publicación de Resultados en Jira Xray

Al finalizar la ejecución, los resultados se publican automáticamente en Jira Xray Cloud, vinculando los escenarios ejecutados con los issues de Jira (usando los tags como `@SCRUM-6`, `@SCRUM-5`, etc.).

---

## 📝 Buenas Prácticas

- Usa tags de Jira en tus escenarios para trazabilidad.
- Mantén tus credenciales seguras y nunca las subas al repositorio.
- Revisa los reportes generados en `target/build/` y en Jira Xray tras cada ejecución.
- Actualiza las dependencias regularmente para mantener el framework seguro y eficiente.
- Contribuye al proyecto siguiendo las normas de codificación y documentación establecidas.
- Utiliza Page Object Model para todas las interacciones con la UI, asegurando una separación clara entre la lógica de negocio y la de presentación.
- Documenta cada nuevo escenario y paso en los archivos `.feature` y en las clases de pasos correspondientes.
- Realiza revisiones de código periódicas para mantener la calidad y coherencia del framework.
