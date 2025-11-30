package base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(60));
    }



    public String obtenerTexto(By elemento) {
        return driver.findElement(elemento).getText();
    }

    public void escribirTexto(String texto, By elemento) {
        driver.findElement(elemento).sendKeys(texto);
    }

    public void hacerClic(By elemento) {
        driver.findElement(elemento).click();
    }

    public boolean estaVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public void seleccionarOpcion(String opcion) {
        By localizadorOpcion = By.xpath("//a[normalize-space()='" + opcion + "']");
        int intentos = 0;
        boolean clicRealizado = false;
        while (intentos < 3 && !clicRealizado) {
            try {
                // Espera explícita para asegurarse de que el elemento sea clickeable
                WebElement elemento = esperarElementoClickeable(localizadorOpcion);
                elemento.click(); // Intenta hacer clic
                clicRealizado = true; // Si tiene éxito, salir del bucle
            } catch (StaleElementReferenceException e) {
                // Si se produce la excepción, intenta nuevamente
                intentos++;
            }
        }
        if (!clicRealizado) {
            throw new RuntimeException("No se pudo hacer clic en el elemento después de 3 intentos debido a StaleElementReferenceException.");
        }
    }

    public WebElement esperarElementoClickeable(By elemento) {
        return wait.until(ExpectedConditions.elementToBeClickable(elemento));
    }

    public WebElement esperarElementoVisible(By elemento) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(elemento));
    }

    public Alert esperarAlertaPresente() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public void navegarA(String url) {
        driver.get(url);
    }

}
