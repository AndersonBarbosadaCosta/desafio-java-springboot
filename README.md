# REST API Products Spring Boot

Web service de produtos utilizando Spring Boot e Swagger para documentação do mesmo.

![APIREST](https://i.imgur.com/aBMs8CR.png"APIREST")

Bibliotecas e tecnologias utilizadas:
- Java 11
- Spring Boot
- Swagger 2
- H2
- Maven

### Instalação

requisitos

- Java 11
- Maven

### Passo a Passo

-  clone o repositório https://github.com/AndersonBarbosadaCosta/desafio-java-springboot.git
- importe o projeto em sua IDE preferida

-  instale as dependências do projeto contidas no arquivo pom.xml contida na pasta raíz do projeto.

- Execute a  classe DesafioApplication.java e o Spring iniciará a mesma na porta 9999.

A url http://localhost:9999/swagger-ui.html contém a documentação da api

Só existe uma rota pública do tipo POST, que retorna o token de autorização através do recurso  http://localhost:9999/auth , passando no corpo da mesma os atributos email  e password .
{
  "email": "admin@email.com",
  "password": "admin",
}

Copie o token gerado do tipo Bearer para ser utilizado como parâmetro Authorization liberando assim os recursos subsequentes.

Podemos conferir por exemplo o recurso GET de products  no endpoint: http://localhost:9999/products

Para execução dos testes automatizados a classe ProductServiceTest deve ser executada.

