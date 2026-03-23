# 📚 Bookshelf API

API REST para gerenciamento de livros e categorias, desenvolvida com Spring Boot 4.0.4 e Java 17.

## 🎯 Sobre o Projeto

Sistema completo para gerenciamento de um acervo de livros que permite:

- ✅ Cadastro e gerenciamento de categorias
- ✅ Cadastro de livros vinculados a categorias
- ✅ Filtro de categorias por nome (case-insensitive)
- ✅ Validação de dados e regras de negócio
- ✅ Tratamento centralizado de erros com respostas padronizadas

## ✨ Funcionalidades

### Categorias

- ✅ Cadastro com nome e descrição
- ✅ Validação de nome único no sistema (case-insensitive)
- ✅ Filtro por nome via query param
- ✅ Atualização com verificação de conflito de nome

### Livros

- ✅ Cadastro de livros vinculados a uma categoria existente
- ✅ Definição de edição: `PRIMEIRA`, `SEGUNDA`, `TERCEIRA`
- ✅ Atualização completa via PUT
- ✅ Validação de duplicidade por nome, autor e edição
- ✅ Exibição do nome da categoria no retorno

## 🛠 Tecnologias Utilizadas

### Backend

- ✅ Java 17
- ✅ Spring Boot 4.0.4
- ✅ Spring Web (MVC)
- ✅ Spring Data JPA
- ✅ Spring Validation
- ✅ Spring DevTools
- ✅ Hibernate (JPA Provider)
- ✅ Lombok — redução de boilerplate
- ✅ ModelMapper 3.2.0 — mapeamento de objetos

### Banco de Dados

- ✅ PostgreSQL

### Documentação

- ✅ SpringDoc OpenAPI 3.0.2 (Swagger UI)

### Testes

- ✅ JUnit 5
- ✅ Mockito
- ✅ Spring Boot Test

### Build

- ✅ Maven

## 🏗 Arquitetura

O projeto segue uma arquitetura em camadas (Controller-Service-Repository):

```
com.thyago.bookshelf_api
├── config/          # Configurações (ModelMapper)
├── controller/      # Endpoints REST
├── dto/             # Data Transfer Objects (Request e Response)
├── entity/          # Entidades JPA
├── enums/           # Enumerações (Edicao)
├── exception/       # Exceções customizadas e handler global
├── repository/      # Repositórios Spring Data JPA
└── service/         # Lógica de negócio
```

## 🚀 Instalação

### Pré-requisitos

- Java 17+
- Maven
- PostgreSQL

### 1. Clone o repositório

```bash
git clone https://github.com/Thyago1992/bookshelf-api.git
cd bookshelf-api
```

### 2. Configure o banco de dados

Crie o banco no PostgreSQL:

```sql
CREATE DATABASE bookshelf_db;
```

### 3. Configure as variáveis de ambiente

Defina a variável de ambiente com a senha do banco:

```bash
export DB_PASSWORD=sua_senha
```

### 4. Compile e execute

```bash
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## ⚙ Configuração

### application.properties

```properties
spring.application.name=bookshelf-api

spring.datasource.url=jdbc:postgresql://localhost:5432/bookshelf_db
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## 🔌 Endpoints da API

### Categorias

#### POST /categorias
Cadastra uma nova categoria.

**Request Body:**
```json
{
  "nome": "Ficção Científica",
  "descricao": "Livros de ficção científica"
}
```

**Response: 201 Created**
```json
{
  "id": 1,
  "nome": "Ficção Científica",
  "descricao": "Livros de ficção científica"
}
```

#### GET /categorias
Lista todas as categorias. Aceita o parâmetro opcional `nome` para filtrar por nome (case-insensitive).

```
GET /categorias
GET /categorias?nome=ficção
```

**Response: 200 OK**

#### GET /categorias/{id}
Retorna uma categoria pelo ID. **Response: 200 OK**

#### PUT /categorias/{id}
Atualiza os dados de uma categoria. **Response: 200 OK**

#### DELETE /categorias/{id}
Remove uma categoria. **Response: 204 No Content**

---

### Livros

#### POST /livros
Cria um novo livro.

**Request Body:**
```json
{
  "nome": "Duna",
  "autor": "Frank Herbert",
  "sinopse": "Uma epopeia galáctica ambientada no planeta deserto Arrakis.",
  "edicao": "PRIMEIRA",
  "idCategoria": 1
}
```

**Response: 201 Created**
```json
{
  "id": 1,
  "nome": "Duna",
  "autor": "Frank Herbert",
  "sinopse": "Uma epopeia galáctica ambientada no planeta deserto Arrakis.",
  "edicao": "PRIMEIRA",
  "nomeCategoria": "Ficção Científica"
}
```

#### GET /livros
Lista todos os livros. **Response: 200 OK**

#### GET /livros/{id}
Retorna um livro pelo ID. **Response: 200 OK**

#### PUT /livros/{id}
Atualiza todos os dados de um livro. **Response: 200 OK**

#### DELETE /livros/{id}
Remove um livro. **Response: 204 No Content**

## 🗄 Estrutura do Banco de Dados

### Tabela: categorias

| Campo    | Tipo    | Restrições         |
|----------|---------|--------------------|
| id       | BIGINT  | PK, AUTO_INCREMENT |
| nome     | VARCHAR | NOT NULL, UNIQUE   |
| descricao| VARCHAR | NOT NULL           |

### Tabela: livros

| Campo       | Tipo    | Restrições                    |
|-------------|---------|-------------------------------|
| id          | BIGINT  | PK, AUTO_INCREMENT            |
| nome        | VARCHAR | NOT NULL                      |
| autor       | VARCHAR | NOT NULL                      |
| sinopse     | TEXT    | NOT NULL                      |
| edicao      | VARCHAR | NOT NULL (enum)               |
| categoria_id| BIGINT  | FK (categorias.id), NOT NULL  |

### Relacionamentos

```
livros.categoria_id → categorias.id (N:1)
```

## ✔ Validações e Regras de Negócio

### Categorias

- ✅ Nome é obrigatório
- ✅ Descrição é obrigatória
- ✅ Nome deve ser único no sistema (ignorando maiúsculas/minúsculas)
- ✅ Na atualização, verifica se o novo nome pertence a outra categoria

### Livros

- ✅ Nome é obrigatório
- ✅ Autor é obrigatório
- ✅ Sinopse é obrigatória
- ✅ Edição é obrigatória (`PRIMEIRA`, `SEGUNDA`, `TERCEIRA`)
- ✅ Categoria vinculada deve existir no sistema
- ✅ Não é permitido cadastrar dois livros com o mesmo nome, autor e edição

## 🛡 Tratamento de Exceções

A API possui tratamento centralizado via `@RestControllerAdvice` com respostas padronizadas.

### Exceções customizadas

| Exceção                           | Status | Descrição                          |
|-----------------------------------|--------|------------------------------------|
| `ObjectNotFoundException`         | 404    | Recurso não encontrado             |
| `AlreadyExistsException`          | 409    | Recurso duplicado no sistema       |
| `MethodArgumentNotValidException` | 400    | Erro de validação de campos        |
| `Exception`                       | 500    | Erro interno do servidor           |

### Formato de resposta de erro

```json
{
  "status": 404,
  "mensagem": "Livro não encontrado",
  "timestamp": "2025-03-21T10:00:00"
}
```

## 📚 Documentação Swagger

A API possui documentação interativa via Swagger UI.

**Acesso:** `http://localhost:8080/swagger-ui/index.html`

A documentação inclui:

- Descrição de todos os endpoints
- Modelos de request e response
- Códigos de status HTTP
- Possibilidade de testar endpoints diretamente

## 💡 Exemplos de Uso

### 1. Cadastrar uma categoria

```bash
curl -X POST http://localhost:8080/categorias \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Ficção Científica",
    "descricao": "Livros de ficção científica"
  }'
```

### 2. Listar categorias filtrando por nome

```bash
curl -X GET "http://localhost:8080/categorias?nome=ficção"
```

### 3. Cadastrar um livro

```bash
curl -X POST http://localhost:8080/livros \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Duna",
    "autor": "Frank Herbert",
    "sinopse": "Uma epopeia galáctica ambientada no planeta deserto Arrakis.",
    "edicao": "PRIMEIRA",
    "idCategoria": 1
  }'
```

### 4. Atualizar um livro

```bash
curl -X PUT http://localhost:8080/livros/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Duna Messias",
    "autor": "Frank Herbert",
    "sinopse": "Continuação de Duna.",
    "edicao": "SEGUNDA",
    "idCategoria": 1
  }'
```

### 5. Deletar um livro

```bash
curl -X DELETE http://localhost:8080/livros/1
```

## 🧪 Testes

O projeto possui testes unitários cobrindo todos os métodos dos services com cenários de sucesso e de erro.

### Executar os testes

```bash
mvn test
```

### Cobertura

| Classe            | Cenários testados                                                                       |
|-------------------|-----------------------------------------------------------------------------------------|
| `CategoriaService`| findAll, findById, findAllByNome, save, update (com e sem mudança de nome), deleteById  |
| `LivroService`    | findAll, findById, save, update, delete — incluindo erros de duplicidade e não encontrado|

## 📝 Licença

Este projeto foi desenvolvido para fins educacionais.

## 👤 Autor

Desenvolvido por **Thyago Antonio Sampaio Valadares** como projeto de portfólio.

[![GitHub](https://img.shields.io/badge/GitHub-Thyago1992-181717?style=flat&logo=github)](https://github.com/Thyago1992)
