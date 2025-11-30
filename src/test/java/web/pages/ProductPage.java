package web.pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    private By etiquetaTitulo = By.className("title");


    public boolean esVisibleEtiquetaTitulo() {
        esperarElementoVisible(etiquetaTitulo);
        return estaVisible(etiquetaTitulo);
    }
}
