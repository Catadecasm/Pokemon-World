# Guía para Desplegar PokemonDemo

En PokemonDemoApplication puedes encontras diferentes funcionalidades dependiento el tipo de usuario que seas, desde una autenticación hasta un combate de peleas! Descúbrelo!

## Requisitos

Asegúrate de tener instalados los siguientes requisitos previos antes de comenzar:

- Java JDK: Debes tener Java instalado en tu máquina.
- Maven: Debes tener Maven instalado en tu máquina.
- Tu aplicación Spring Boot lista para ser desplegada. Puedes crear una aplicación Spring Boot utilizando Spring Initializr.
Lo único que necesitarás para abrir un documento Markdown es un editor de texto plano.


## Pasos para Desplegar la Aplicación

### Paso 1: Empaquetar la Aplicación

Usa Maven para empaquetar tu aplicación Spring Boot. Abre una terminal y navega al directorio raíz de tu proyecto y ejecuta el siguiente comando:

`mvn clean package`

Este comando compilará tu proyecto y creará un archivo JAR ejecutable en el directorio target.

### Paso 2: Ejecutar la Aplicación

Para ejecutar la aplicación, simplemente utiliza el comando java -jar seguido del nombre del archivo JAR.

### Paso 3: Acceder a la Aplicación
Una vez que la aplicación se haya iniciado correctamente, puedes acceder a ella en tu navegador web utilizando la URL http://localhost:puerto. Por defecto, Spring Boot ejecuta la aplicación en el puerto 8080


