<h1 align="center">
  ☕ Partenon API
</h1>

## ℹ️ Introduction
Este repositorio se encuentra una API para museos virtuales, proyecto desarrollado para la Olimpiada de Programación 2022. En este caso este es una version modificada de la API que hice para la olimpiada, la diferencia principal se encuentra en que en lugar de utilizar Spring-Web hice la migracion a Spwing-WebFlux libreria para utilizar programacion reactiva. Para mas informacion sobre Programacion [Reactiva en Spring](https://www2.deloitte.com/es/es/pages/technology/articles/la-programacion-reactiva-en-spring.html)

## 🏁 Antes de iniciar:
  * `1.` Instalar Java 17: `SITIO OFICIAL:` [OpenJDK 17](https://jdk.java.net/archive)
  * `2.` Instalar MySQL 8: `SITIO OFICIAL:` [MySQL 8](https://dev.mysql.com/downloads/mysql/8.0.html).
  * `3.` En caso de no contar con un IDE (Estos suelen tener Maven integrado) debera isntalar Maven `Sitio Oficial: ` [Apache Maven](https://maven.apache.org/download.cgi?.).
  * `4.` **RECOMENDACION PERSONAL** Utilizar el IDE [IntellIj IDEA](https://www.jetbrains.com/idea/download/?fromIDE=#section=windows)


## Como iniciar
  * `1.` Clonar el repositorio: `git clone https://github.com/conjunto-solucion/facturador.git`
  * `2.` La base de datos se encuentra en: `.../facturador/data` Ejecutar este con su MySQL
  * `3.` Revisar el archivo application.yml se encuentran `.../facturador/src/main/resources/`
    * 1. La parte que nos importe aqui es esta:
```yml
#Buscamos la linea que diga esto y cambiar con sus datos
  datasource:
    password: #YOUR_PASSWORD
    #DNS CONEXION A MYSQL
    url: jdbc:mysql://localhost:3306/facturador_db?useSSL=true&useTimezone=true&serverTimezone=UTC
    username: #YOUR_USERNAME
#Aqui habra mas configuraciones modificarlas a gusto
```

## Como actualizar dependencias
  * `1.` Limpiar el Build e instalar dependencias: `mvn clean -f pom.xml && mvn install -f pom.xml`.
    * 1. Puede hacerlo desde la interfaz grafica de IntelliJ **RECOMENDABLE**
    * 2. Desde la interfaz grafica click derecho al archivo pom.xml opcion Maven > Reload project
    * 3. En consola debe estar en la ruta del pom.xml

