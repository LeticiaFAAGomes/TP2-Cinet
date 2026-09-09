# 🎬 Cinet

### Teste de Performance 2 — Docker e Kubernetes

#### Sistema de Gerenciamento de Filmes e Sessões com Spring Boot

[![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white)](https://kubernetes.io/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)

Sistema de gerenciamento de **filmes e sessões de cinema**, desenvolvido utilizando uma arquitetura baseada em **microsserviços**, com **Spring Boot**, **Docker**, **Docker Compose** e **Kubernetes**.

O projeto foi desenvolvido com o objetivo de aplicar conceitos de microsserviços, comunicação entre serviços, conteinerização, redes Docker, orquestração com Kubernetes, criação de réplicas e distribuição de requisições.

---

## 📌 Objetivos

- Desenvolver uma aplicação utilizando arquitetura de microsserviços.
- Separar as responsabilidades do sistema em serviços independentes.
- Criar um microsserviço responsável pelo gerenciamento de filmes.
- Criar um microsserviço responsável pelo gerenciamento de sessões.
- Utilizar bancos de dados independentes para cada microsserviço.
- Implementar comunicação entre os microsserviços.
- Criar imagens Docker para os microsserviços.
- Utilizar Docker Network para comunicação entre containers.
- Utilizar Docker Compose para executar os serviços em conjunto.
- Migrar a aplicação para Kubernetes.
- Utilizar Deployments e Services no Kubernetes.
- Criar múltiplas réplicas de um microsserviço.
- Utilizar o Service para distribuir as requisições entre os Pods.

---

## 🎬 Sobre o Sistema

O **Cinet** é um sistema distribuído voltado ao gerenciamento de filmes e sessões de cinema.

A aplicação é dividida em dois microsserviços principais:

- **Cinet Filme** — responsável pelo cadastro e gerenciamento dos filmes.
- **Cinet Sessão** — responsável pelo cadastro e gerenciamento das sessões.

O microsserviço de sessões realiza uma comunicação com o microsserviço de filmes para verificar se o filme associado à sessão existe antes de realizar o cadastro.

---

## 🏛️ Arquitetura

A aplicação utiliza uma arquitetura baseada em **microsserviços**, na qual cada serviço possui sua própria responsabilidade e seu próprio banco de dados.

```text
                         ┌─────────────────────────┐
                         │         CLIENTE         │
                         │       REST / HTTP       │
                         └────────────┬────────────┘
                                      │
                    ┌─────────────────┴─────────────────┐
                    │                                   │
                    ▼                                   ▼
          ┌─────────────────┐                 ┌─────────────────┐
          │ CINET-FILME     │                 │ CINET-SESSAO    │
          │      :8081      │◄────────────────│      :8082      │
          └────────┬────────┘    comunicação  └────────┬────────┘
                   │                                   │
                   ▼                                   ▼
          ┌─────────────────┐                 ┌─────────────────┐
          │   PostgreSQL    │                 │   PostgreSQL    │
          │     Filmes      │                 │     Sessões     │
          └─────────────────┘                 └─────────────────┘
```

O serviço de sessões consulta o serviço de filmes antes de criar uma sessão.

---

## 🧩 Componentes da Aplicação

| Componente               | Porta | Responsabilidade          |
| ------------------------ | ----: | ------------------------- |
| **cinet-filme-service**  |  8081 | Gerenciamento dos filmes  |
| **cinet-sessao-service** |  8082 | Gerenciamento das sessões |
| **PostgreSQL Filmes**    |  5432 | Persistência dos filmes   |
| **PostgreSQL Sessões**   |  5433 | Persistência das sessões  |

---

# 🎬 Microsserviço de Filmes

O `cinet-filme-service` é responsável pelo gerenciamento dos filmes.

### Responsabilidades

- Cadastrar filmes.
- Listar filmes.
- Buscar filme por ID.
- Excluir filmes.
- Persistir os dados dos filmes em seu próprio banco de dados.

### Porta

```text
8081
```

---

# 🎟️ Microsserviço de Sessões

O `cinet-sessao-service` é responsável pelo gerenciamento das sessões.

### Responsabilidades

- Cadastrar sessões.
- Listar sessões.
- Buscar sessão por ID.
- Excluir sessões.
- Consultar o microsserviço de filmes.
- Persistir os dados das sessões em seu próprio banco de dados.

### Porta

```text
8082
```

---

# 🔄 Comunicação entre Microsserviços

O `cinet-sessao-service` precisa consultar o `cinet-filme-service` para verificar se o filme informado existe.

Essa comunicação é realizada pelo `FilmeClient` utilizando `RestClient`.

```text
CINET-SESSAO-SERVICE
        │
        │ GET /filmes/{id}
        ▼
CINET-FILME-SERVICE
        │
        ▼
     Resposta
```

A comunicação utiliza o nome do serviço:

```text
http://cinet-filme-service:8081
```

Dessa forma, os microsserviços conseguem se comunicar dentro da rede Docker e também no Kubernetes sem utilizar `localhost`.

---

# 🐳 Docker

Foram criados Dockerfiles para os dois microsserviços.

```text
cinet-filme-service/
└── Dockerfile

cinet-sessao-service/
└── Dockerfile
```

Os Dockerfiles utilizam uma imagem Java para executar os arquivos `.jar` gerados pelo Maven.

### Cinet Filme

```dockerfile
FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Cinet Sessão

```dockerfile
FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

# 🌐 Docker Network

Foi criada uma rede Docker para permitir a comunicação entre os containers.

### Rede

```text
cinet-network
```

### Comando utilizado

```bash
docker network create cinet-network
```

A arquitetura da rede ficou:

```text
                    cinet-network
                         |
              +----------+----------+
              |                     |
              ▼                     ▼
     cinet-filme-service    cinet-sessao-service
              |                     |
              ▼                     ▼
     cinet-postgres-filmes  cinet-postgres-sessoes
```

Os containers utilizam os nomes dos serviços para realizar a comunicação dentro da rede.

---

# 🐳 Docker Compose

O projeto possui um arquivo `docker-compose.yml` para configurar e executar os microsserviços e seus bancos de dados em conjunto.

### Serviços

```text
postgres-filmes
postgres-sessoes
cinet-filme-service
cinet-sessao-service
```

### Executar

```bash
docker compose up -d
```

### Verificar os containers

```bash
docker compose ps
```

O Docker Compose também configura a rede `cinet-network` e os volumes utilizados pelos bancos PostgreSQL.

---

# 🗄️ Persistência de Dados

Cada microsserviço possui seu próprio banco de dados.

### Cinet Filme

```text
cinet_filmes
```

### Cinet Sessão

```text
cinet_sessoes
```

A separação dos bancos evita que os microsserviços compartilhem diretamente a mesma estrutura de persistência.

---

# ☸️ Kubernetes

Após a execução utilizando Docker e Docker Compose, a aplicação foi preparada para execução no Kubernetes.

Foram utilizados:

- Deployments;
- Pods;
- Services;
- PersistentVolumeClaims;
- PostgreSQL;
- Réplicas.

---

## 📦 Deployments

Foram criados Deployments para os microsserviços:

```text
cinet-filme-deployment
cinet-sessao-deployment
```

O Deployment do serviço de filmes foi configurado com três réplicas.

```yaml
spec:
  replicas: 3
```

---

## 🔗 Services

Foram criados Services para disponibilizar os microsserviços dentro do cluster:

```text
cinet-filme-service
cinet-sessao-service
```

Os Services permitem que os Pods sejam acessados por um nome estável, mesmo quando os Pods são recriados.

---

# 📈 Réplicas e Balanceamento

O `cinet-filme-service` foi configurado com três réplicas.

```text
                 cinet-filme-service
                         |
              +----------+----------+
              |          |          |
              ▼          ▼          ▼
            Pod 1      Pod 2      Pod 3
```

Os três Pods executam o mesmo microsserviço.

O Service recebe as requisições e encaminha para os Pods disponíveis.

### Verificar os Pods

```bash
kubectl get pods
```

### Verificar o Service

```bash
kubectl get services
```

### Verificar os endpoints

```bash
kubectl get endpoints cinet-filme-service
```

---

# 🗃️ PostgreSQL no Kubernetes

Os bancos PostgreSQL também foram configurados no Kubernetes.

Foram criados:

```text
cinet-postgres-filmes
cinet-postgres-sessoes
```

Cada banco possui seu próprio Deployment, Service e PersistentVolumeClaim.

```text
Cinet Filme
     │
     ▼
cinet-postgres-filmes

Cinet Sessão
     │
     ▼
cinet-postgres-sessoes
```

---

# 🔗 Endpoints

## 🎬 Filmes

### `cinet-filme-service`

| Método | Endpoint       | Descrição             |
| ------ | -------------- | --------------------- |
| GET    | `/filmes`      | Lista todos os filmes |
| POST   | `/filmes`      | Cadastra um filme     |
| GET    | `/filmes/{id}` | Busca um filme por ID |
| DELETE | `/filmes/{id}` | Remove um filme       |

---

## 🎟️ Sessões

### `cinet-sessao-service`

| Método | Endpoint        | Descrição               |
| ------ | --------------- | ----------------------- |
| GET    | `/sessoes`      | Lista todas as sessões  |
| POST   | `/sessoes`      | Cadastra uma sessão     |
| GET    | `/sessoes/{id}` | Busca uma sessão por ID |
| DELETE | `/sessoes/{id}` | Remove uma sessão       |

---

# 🧪 Testes

Foram realizados testes dos endpoints dos dois microsserviços utilizando Swagger.

Também foram realizados testes de comunicação entre os serviços.

### Teste de criação de sessão

Para criar uma sessão, é necessário informar um `filmeId`.

O `cinet-sessao-service` consulta o:

```text
cinet-filme-service
```

para verificar se o filme existe.

O fluxo fica:

```text
POST /sessoes
      │
      ▼
cinet-sessao-service
      │
      ▼
Consulta o filme
      │
      ▼
cinet-filme-service
      │
      ▼
Filme encontrado
      │
      ▼
Sessão cadastrada
```

Os testes foram realizados tanto utilizando os containers Docker/Compose quanto os serviços executando no Kubernetes.

---

# 🔎 Verificação do Kubernetes

Para verificar o estado dos recursos utilizados no cluster:

### Pods

```bash
kubectl get pods
```

### Deployments

```bash
kubectl get deployments
```

### Services

```bash
kubectl get services
```

### Endpoints

```bash
kubectl get endpoints
```

O serviço de filmes foi executado com três Pods ativos.

---

# 📁 Estrutura do Projeto

```text
cinet-filme-service/
│
├── .mvn/
│   └── wrapper/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/edu/infnet/cinet_filme_service/
│   │   │       ├── application/
│   │   │       │   └── service/
│   │   │       │       └── FilmeService.java
│   │   │       │
│   │   │       ├── domain/
│   │   │       │   ├── models/
│   │   │       │   └── valueObjects/
│   │   │       │
│   │   │       ├── infrastructure/
│   │   │       │   └── persistence/
│   │   │       │       ├── entity/
│   │   │       │       └── repository/
│   │   │       │
│   │   │       ├── interfaces/
│   │   │       │   ├── controllers/
│   │   │       │   └── dtos/
│   │   │       │
│   │   │       └── CinetFilmeServiceApplication.java
│   │   │
│   │   └── resources/
│   │
│   └── test/
│       └── java/
│           └── br/edu/infnet/cinet_filme_service/
│
├── cinet-sessao-service/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── eureka-server/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── k8s/
│   ├── cinet-sessao-service/
│   ├── filme-deployment.yml
│   ├── filme-postgres.yml
│   ├── filme-service.yml
│   ├── sessao-deployment.yml
│   ├── sessao-postgres.yml
│   └── sessao-service.yml
│
├── .gitattributes
├── .gitignore
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

# 🛠️ Tecnologias

- **Java 25**
- **Spring Boot 4.1.1**
- **Spring Web**
- **Spring Data JPA**
- **PostgreSQL 16**
- **Maven**
- **Docker**
- **Docker Compose**
- **Kubernetes**
- **Swagger / OpenAPI**
- **RestClient**

---

## 🔐 Configuração do ambiente

Antes de executar o projeto, é necessário criar alguns arquivos de configuração que não são versionados no Git.

### `.env`

Na **raiz do projeto**, crie um arquivo chamado `.env`:

```env
POSTGRES_PASSWORD=sua_senha
```

Substitua `sua_senha` pela senha que será utilizada pelos bancos PostgreSQL.

### `application-local.properties`

Também é necessário criar o arquivo `application-local.properties` nos dois microsserviços.

No **cinet-filme-service**, crie o arquivo em:

```text
src/main/resources/application-local.properties
```

com a configuração:

```properties
DB_PASSWORD=sua_senha
```

No **cinet-sessao-service**, crie o arquivo em:

```text
src/main/resources/application-local.properties
```

com:

```properties
DB_PASSWORD=sua_senha
```

Utilize a mesma senha definida no `.env`.

# 🚀 Como Executar

## 1. Gerar os projetos

Entre em cada microsserviço e execute:

```bash
mvn clean package
```

---

## 2. Criar as imagens Docker

### Filme

```bash
docker build -t cinet-filme-service .
```

### Sessão

```bash
docker build -t cinet-sessao-service .
```

---

## 3. Executar com Docker Compose

Na raiz do projeto:

```bash
docker compose up -d
```

Verifique:

```bash
docker compose ps
```

---

## 4. Executar no Kubernetes

Com o Kubernetes ativo, aplique os arquivos:

```bash
kubectl apply -f k8s/filme-postgres.yml
kubectl apply -f k8s/filme-deployment.yml
kubectl apply -f k8s/filme-service.yml

kubectl apply -f k8s/sessao-postgres.yml
kubectl apply -f k8s/sessao-deployment.yml
kubectl apply -f k8s/sessao-service.yml
```

Verifique os Pods:

```bash
kubectl get pods
```

Verifique os Services:

```bash
kubectl get services
```

Verifique os Deployments:

```bash
kubectl get deployments
```

---

# 📚 Conceitos Aplicados

O projeto utiliza os seguintes conceitos:

- Arquitetura de microsserviços;
- Separação de responsabilidades;
- APIs REST;
- Comunicação entre microsserviços;
- Docker;
- Dockerfile;
- Docker Image;
- Docker Container;
- Docker Network;
- Docker Compose;
- Kubernetes;
- Pods;
- Deployments;
- Services;
- Réplicas;
- Balanceamento de requisições;
- Persistência de dados;
- PostgreSQL;
- Service Discovery dentro do Kubernetes.

---

# 👩‍💻 Autora

**Letícia Gomes**

Projeto desenvolvido individualmente para a disciplina de **Desenvolvimento de Softwares Escaláveis**, aplicando conceitos de microsserviços, Docker, Docker Compose e Kubernetes.
