# Convertidor MySQL-Excel
Programa que permite extraer los datos de una base MySQL para crear un archivo Excel con ellos.
Tambien es posible crear una base de datos nueva con el contenido de un libro de trabajo de Excel.

## Características
### Exportación de bases de datos
* Acceso al sistema con las credenciales de usuario
* Se incluye un panel donde es posible visualizar las tablas de la base seleccionada
* Las celdas del archivo creado pertenecen al tipo de dato obtenido desde la base
* Es posible ver el progreso y sus detalles durante el proceso de exportación

### Importación de bases de datos
* Se incluye un panel para visualizar las hojas del archivo abierto
* Creación de un archivo Excel en el que cada hoja corresponde con una tabla de la base de datos
* El usuario puede seleccionar cuales tablas se van a incluir en el archivo
* Creación de una base de datos nueva utilizando los datos del archivo Excel abierto en el programa
* El usuario puede seleccionar las hojas del archivo que serán convertidas en tablas
* Antes de crear la base, el usuario puede configurar el tipo de dato y los modificadores de cada columna en la hoja, los cuales son utilizados para definir la estructura de la tabla nueva.
* Es posible ver el progreso y sus detalles durante el proceso de importación

## Lenguajes y herramientas
* El proyecto está escrito completamente en el lenguaje Java
* Para establecer la conexión con el servidor de bases de datos se utilizó la librería mysql-connector-java-5.1.18
* Para crear los archivos Excel se utilizó la librería Apache POI 4.1.1

## Créditos
* Imágen de pantalla principal: []() por []()