package web.pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private By campoNombreUsuario = By.id("user-name");
    private By campoContrasena = By.id("password");
    private By botonIniciarSesion = By.id("login-button");
    private By mensajeError = By.cssSelector("h3[data-test='error']");

    public void cargarPaginaInicio(){
        navegarA("https://www.saucedemo.com/");
    }

    public void ingresarNombreUsuario(String nombreUsuario){
        esperarElementoVisible(campoNombreUsuario);
        escribirTexto(nombreUsuario,campoNombreUsuario);
    }

    public void ingresarContrasena(String password){
        escribirTexto(password,campoContrasena);
    }

    public void hacerClicEnIniciarSesion(){
        hacerClic(botonIniciarSesion);
    }

    public String obtenerMensajeError() {
        esperarElementoVisible(mensajeError);
        return obtenerTexto(mensajeError);
    }

}
