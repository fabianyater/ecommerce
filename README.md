# Ecommerce - Prueba Técnica

API Rest para gestionar la creación de productos para un ecommerce.

## Estructura del Proyecto

- `controller`: Contiene los controladores REST.
- `service`: Contiene los servicios de la aplicación.
- `repository`: Contiene los repositorios de la base de datos.
- `model`: Contiene los modelos de datos.
- `dto`: Contiene los objetos de transferencia de datos.
- `exception`: Contiene las excepciones personalizadas.
- `mapper`: Contiene las clases de mapeo de objetos.
- `config`: Contiene la configuración de la aplicación.

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

### Configuración del Proyecto

Antes de ejecutar la aplicación, asegúrate de configurar el archivo de propiedades application.yaml con los siguientes valores:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/ecommerce

weather:
  api:
    key: a856214d721003d0d6484729aa921a07
    url: http://api.openweathermap.org/data/2.5/weather
```

- MongoDB URI: La URI de la base de datos MongoDB.
- Weather API Key: La clave de la API de OpenWeatherMap.

### Pasos para configurar el archivo

1. Navega al directorio `src/main/resources` dentro del proyecto.
2. Crea un archivo llamado `application.yaml` o cambia el nombre del archivo `application.properties` a `application.yaml`.
3. Copia y pega el contenido anterior en el archivo.

3. Ejecuta la aplicación utilizando Gradle.
```
    ./gradlew bootRun
```

## Endpoints de la API

Los endpoints para la gestión de productos estarán disponibles en [http://localhost:8080/api/v1/products](http://localhost:8080/api/v1/products). 
La documentación de la API se encuentra en [Swagger UI Docs](http://localhost:8080/swagger-ui/index.html).

## Pruebas Unitarias

Las pruebas unitarias se encuentran en el directorio src/test/java. Hemos utilizado JUnit y Mockito para asegurarnos de
que la lógica de negocio funcione correctamente.

Para ejecutar las pruebas, puedes utilizar el siguiente comando:

```
./gradlew test
```

## Covertura de Pruebas

La cobertura de pruebas se ha realizado utilizando JaCoCo. 
Puedes ver el informe completo abriendo el [informe HTML](http://localhost:63342/ecommerce/build/reports/jacocoHtml/index.html?_ijt=tpcvfpoiathuac8b3pcl3pjpgb&_ij_reload=RELOAD_ON_SAVE) generado después de ejecutar las pruebas.

## Ejecutar aplicación usando Docker

Una vez el proyecto esté configurado, vas a compilar el proyecto con el siguiente comando:

```
./gradlew bootBuild
```

Luego, vas a ejecutar el comando de Docker compose para crear las imagenes y crear el contenedor de la aplicación:

```
docker compose up
```

A este momento, se descargarán las imágenes de Mongo y Java y se procederá a ejecutar el proyecto