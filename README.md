# TwitterAPI

Este repositorio contiene el código fuente de una aplicación para la publicación de anuncios de clases particulares. Desarrollado como parte del Trabajo Fin de Grado (TFG) de Ignacio Ruiz, este proyecto permite a los usuarios gestionar anuncios utilizando una API personalizada.

## Tabla de Contenidos
- [Descripción del Proyecto](#descripción-del-proyecto)
- [Características](#características)
- [Arquitectura](#arquitectura)
- [Paquetes](#paquetes)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Uso](#uso)
- [Endpoints de la API](#endpoints-de-la-api)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)
- [Contacto](#contacto)

## Descripción del Proyecto
El proyecto TwitterAPI está diseñado para facilitar la publicación y gestión de anuncios de clases particulares. Incluye funcionalidades para crear, editar y eliminar anuncios, así como para gestionar la información de los usuarios y sus anuncios.

## Características
- **Publicación de Anuncios**: Permite a los usuarios publicar anuncios de clases particulares.
- **Gestión de Anuncios**: Funcionalidades para editar y eliminar anuncios existentes.
- **Perfil de Usuario**: Manejo de la información del usuario y sus anuncios.

## Arquitectura
El proyecto sigue una arquitectura modular con los siguientes componentes principales:
- **Módulo Principal**: Punto de entrada de la aplicación.
- **Módulo de Anuncios**: Maneja la creación, edición y eliminación de anuncios.
- **Módulo de Usuarios**: Maneja la información y autenticación de los usuarios.

## Paquetes
- **persistence**: Manejo de la persistencia de datos.
- **dto**: Objetos de transferencia de datos.
- **services**: Lógica de negocio y servicios.
- **controllers**: Controladores de la API.
- **security**: Configuración de seguridad.
- **jwt**: Manejo de JSON Web Tokens para autenticación.
- **config**: Configuración general de la aplicación.

## Requisitos Previos
- Java Development Kit (JDK) 8 o superior
- Cuenta de desarrollador de Twitter con claves de API (si se utiliza para autenticar usuarios)

## Instalación

1. **Clonar el Repositorio**:
   ```bash
   git clone https://github.com/IgnacioRuiz01/api-TFG.git
   cd api-TFG
2.**Compilar el Proyecto**:
```bash
javac -d bin src/**/*.java

3.**Ejecutar el Proyecto**:
```bash
java -cp bin Main

## Configuración

Configurar las Claves de API de Twitter (si aplica):

1. Crear un archivo `.env` en el directorio raíz.
2. Agregar las credenciales de la API de Twitter:

   ```plaintext
   TWITTER_API_KEY=tu_api_key
   TWITTER_API_SECRET_KEY=tu_api_secret_key
   TWITTER_ACCESS_TOKEN=tu_access_token
   TWITTER_ACCESS_TOKEN_SECRET=tu_access_token_secret

## Uso

Después de configurar y ejecutar el proyecto, los usuarios pueden interactuar con la aplicación a través de los endpoints de la API para crear, editar y gestionar anuncios de clases particulares.

## Endpoints de la API

### Crear Anuncio

- **Endpoint**: `/api/anuncios`
- **Método**: `POST`
- **Parámetros**: `titulo`, `descripcion`, `precio`, `usuario_id`
- **Descripción**: Crea un nuevo anuncio de clase particular.

### Editar Anuncio

- **Endpoint**: `/api/anuncios/{id}`
- **Método**: `PUT`
- **Parámetros**: `titulo`, `descripcion`, `precio`
- **Descripción**: Edita un anuncio existente.

### Eliminar Anuncio

- **Endpoint**: `/api/anuncios/{id}`
- **Método**: `DELETE`
- **Descripción**: Elimina un anuncio existente.

### Obtener Anuncios de Usuario

- **Endpoint**: `/api/usuarios/{usuario_id}/anuncios`
- **Método**: `GET`
- **Descripción**: Obtiene todos los anuncios de un usuario específico.

## Tecnologías Utilizadas

- **Lenguaje de Programación**: Java
- **API**: API personalizada para gestión de anuncios
- **Librerías**: Varias librerías de Java para solicitudes HTTP y manejo de JSON
- **Base de Datos**: Sistema de gestión de bases de datos (por ejemplo, MySQL, PostgreSQL)

## Contribuciones

Las contribuciones son bienvenidas. Por favor, sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama (`git checkout -b feature-branch`).
3. Realiza tus cambios (`git commit -m 'Añadir nueva característica'`).
4. Haz push a la rama (`git push origin feature-branch`).
5. Crea un nuevo Pull Request.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.

## Contacto

Para cualquier consulta o problema, por favor contacta a Ignacio Ruiz en [ignacioidigorass@gmail.com](mailto:ignacioidigorass@gmail.com).








