@SCRUM-8
Feature: Inicio de sesión en Sauce Demo
  Verifica que el inicio de sesión funcione correctamente y que se muestren errores con credenciales inválidas.

  Background:
    Given que el usuario abre la página de login


  @SCRUM-6
  Scenario: Inicio de sesión exitoso con credenciales válidas
    When el usuario inicia sesión con usuario "standard_user" y contraseña "secret_sauce"
    Then debería ver la página de productos

  @SCRUM-5
  Scenario Outline:Inicio de sesión fallido con credenciales inconrrectas
    When el usuario inicia sesión con usuario "<usuario>" y contraseña "<contrasena>"
    Then debería ver un mensaje de error que contenga "<mensaje>"

    Examples:
      | usuario         | contrasena   | mensaje                                                                   |
      | locked_out_user | secret_sauce | Epic sadface: Sorry, this user has been locked out.                       |
      | invalid_user    | wrong_pass   | Epic sadface: Username and password do not match any user in this service |
