# Ecommerce - Prueba Técnica

API Rest para gestionar la creación de productos para un ecommerce.

## Tecnologías y Herramientas Utilizadas

- **Java 17**
- **Spring Boot**
- **Gradle**
- **MongoDB** como base de datos.
- **MapStruct** para mapeo de objetos.
- **SpringDoc OpenAPI UI** para documentación de la API.

## Pre-requisitos

- **Java 17**: Asegúrate de tener instalado Java 17 en tu sistema.
- **MongoDB**: Una instancia de MongoDB ejecutándose en localhost:27017.
- **Docker Desktop**: Asegurate de tener Docker Desktop instalado (Windows).

## Ejecutar la Aplicación

1. Clona el repositorio en tu sistema.
 ```
    git clone https://github.com/fabianyater/ecommerce.git
```
2. Navega al directorio del proyecto y abre el proyecto con tu IDE favorito (IntelliJ se usó para este proyecto).
3. Configurar Claves de API: Crea el archivo application.yaml en ``src/main/resources`` con el siguiente contenido:
    
    ```yaml
    spring:
      data:
        mongodb:
          uri: mongodb://localhost:27017/ecommerce
    
    weather:
      api:
        key: TU_CLAVE_DE_API
        url: http://api.openweathermap.org/data/2.5/weather
    ```
   
Puedes obtener una clave de API de OpenWeatherMap [aquí](https://home.openweathermap.org/api_keys).

- MongoDB URI: La URI de la base de datos MongoDB.
- Weather API Key: La clave de la API de OpenWeatherMap.

3. Ejecuta la aplicación localmente
```
    ./gradlew bootRun
```

## Probar los endpoints de la API

Con la aplicación en ejecución, puedes acceder a los endpoints de la API. Los endpoints para la gestión de productos estarán disponibles en [Swagger UI Docs](http://localhost:8080/swagger-ui/index.html).

## Pruebas Unitarias

Las pruebas unitarias se encuentran en el directorio ``src/test/java``. Hemos utilizado JUnit y Mockito para asegurarnos de
que la lógica de negocio funcione correctamente.

Para ejecutar las pruebas, puedes utilizar el siguiente comando:

```
./gradlew test
```

## Covertura de Pruebas

La cobertura de pruebas se ha realizado utilizando JaCoCo. 
Puedes ver el informe completo abriendo el [informe HTML](http://localhost:63342/ecommerce/build/reports/jacocoHtml/index.html?_ijt=tpcvfpoiathuac8b3pcl3pjpgb&_ij_reload=RELOAD_ON_SAVE) generado después de ejecutar las pruebas.

## Ejecutar aplicación usando Docker

Para ejecutar la aplicación utilizando Docker, primero debes limpiar y construir el proyecto con Gradle:

```
./gradlew clean
```
```
./gradlew build
```

Luego, puedes construir la imagen de Docker con el siguiente comando:

```
docker compose up
```

La aplicación se ejecutará en un contenedor Docker y estará disponible en [http://localhost:8080](http://localhost:8080).