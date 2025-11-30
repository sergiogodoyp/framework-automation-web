package web.stepdefinitions;

import driver.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import web.pages.LoginPage;
import web.pages.ProductPage;

public class LoginStepDefinition {

    LoginPage loginPage;
    ProductPage productPage;

    public LoginStepDefinition() {
        loginPage = new LoginPage(DriverManager.getDriver());
        productPage = new ProductPage(DriverManager.getDriver());
    }

    @Given("que el usuario abre la página de login")
    public void queElUsuarioAbreLaPáginaDeLogin() {
        loginPage.cargarPaginaInicio();
    }


    @When("el usuario inicia sesión con usuario {string} y contraseña {string}")
    public void elUsuarioIniciaSesiónConUsuarioYContraseña(String usuario, String contrasena) {
        loginPage.ingresarNombreUsuario(usuario);
        loginPage.ingresarContrasena(contrasena);
        loginPage.hacerClicEnIniciarSesion();
    }

    @Then("debería ver la página de productos")
    public void deberíaVerLaPáginaDeProductos() {
        Assert.assertTrue(productPage.esVisibleEtiquetaTitulo());
    }


    @Then("debería ver un mensaje de error que contenga {string}")
    public void deberíaVerUnMensajeDeErrorQueContenga(String mensajeEsperado) {
        String mensajeError = loginPage.obtenerMensajeError();
        Assert.assertEquals(mensajeEsperado, mensajeError);
    }
}
