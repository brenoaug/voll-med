# Voll.med API

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.9-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

API REST para gerenciamento de clínica médica, desenvolvida com Spring Boot. O sistema permite o cadastro, listagem, atualização e exclusão lógica de médicos e pacientes, além de autenticação JWT.

## Índice

- [Tecnologias](#tecnologias)
- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Executando o Projeto](#executando-o-projeto)
- [Endpoints da API](#endpoints-da-api)
- [Autenticação](#autenticação)
- [Tratamento de Erros](#tratamento-de-erros)
- [Migrações de Banco de Dados](#migrações-de-banco-de-dados)
- [Testes](#testes)
- [Build](#build)
- [Contribuição](#contribuição)

## Tecnologias

### Core
- **Java 17** - Linguagem de programação
- **Spring Boot 3.5.9** - Framework para aplicações Java
- **Maven** - Gerenciamento de dependências e build

### Spring Framework
- **Spring Web** - Criação de APIs REST
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Autenticação e autorização
- **Spring Validation** - Validação de dados

### Banco de Dados
- **MySQL** - Sistema de gerenciamento de banco de dados
- **Flyway** - Versionamento e migração de banco de dados

### Segurança
- **JWT (JSON Web Token)** - Autenticação via token (Auth0 java-jwt 4.2.1)
- **BCrypt** - Criptografia de senhas

### Documentação
- **SpringDoc OpenAPI** - Documentação automática da API (Swagger/OpenAPI 3.0)

### Utilitários
- **Lombok** - Redução de código boilerplate
- **Spring DevTools** - Ferramentas de desenvolvimento

## Funcionalidades

### Autenticação
- Login com usuário e senha
- Geração de token JWT
- Proteção de rotas com autenticação

### Gerenciamento de Médicos
- Cadastro de médicos com validação de dados
- Listagem paginada de médicos ativos
- Atualização de informações de médicos
- Exclusão lógica (soft delete)
- Especialidades suportadas:
  - Ortopedia
  - Cardiologia
  - Ginecologia
  - Dermatologia

### Gerenciamento de Pacientes
- Cadastro de pacientes com validação de dados
- Listagem paginada de pacientes ativos
- Atualização de informações de pacientes
- Exclusão lógica (soft delete)
- CPF único por paciente
- Validação de telefone e endereço

### Agendamento de Consultas
- Agendamento de consultas médicas
- Validação de horários de funcionamento (segunda a sábado, 07:00 às 19:00)
- Validação de antecedência mínima (30 minutos)
- Escolha automática de médico disponível por especialidade
- Validações de negócio:
  - Médico ativo
  - Paciente ativo
  - Médico sem outra consulta no mesmo horário
  - Paciente sem outra consulta no mesmo dia
- Cancelamento de consultas com antecedência mínima de 24 horas
- Motivos de cancelamento:
  - Paciente desistiu
  - Médico cancelou
  - Outros

## Arquitetura

O projeto segue uma arquitetura em camadas:

```
src/main/java/med/voll/api/
├── ApiApplication.java      # Classe principal da aplicação
├── controller/              # Controladores REST (Camada de apresentação)
│   ├── AutenticacaoController.java
│   ├── ConsultaController.java
│   ├── MedicosController.java
│   └── PacientesController.java
├── dto/                     # Data Transfer Objects
│   ├── DadosAgendamentoConsulta.java
│   ├── DadosAutenticacao.java
│   ├── DadosCadastroMedico.java
│   ├── DadosCadastroPaciente.java
│   ├── DadosCancelamentoConsulta.java
│   ├── DadosAtualizacaoMedico.java
│   ├── DadosAtualizacaoPaciente.java
│   ├── DadosDetalhamentoConsulta.java
│   ├── DadosDetalhamentoMedico.java
│   ├── DadosDetalhamentoPaciente.java
│   ├── DadosListagemMedico.java
│   ├── DadosListagemPaciente.java
│   ├── DadosEndereco.java
│   ├── DadosTokenJWT.java
│   └── enums/
│       ├── Especialidade.java
│       ├── MotivoCancelamento.java
│       └── Uf.java
├── entities/                # Entidades JPA (Camada de domínio)
│   ├── Consulta.java
│   ├── Medico.java
│   ├── Paciente.java
│   ├── Usuario.java
│   └── Endereco.java        # Classe @Embeddable
├── repository/              # Repositórios JPA (Camada de dados)
│   ├── ConsultaRepository.java
│   ├── MedicoRepository.java
│   ├── PacienteRepository.java
│   └── UsuarioRepository.java
├── security/                # Configurações de segurança
│   ├── SecurityFilter.java
│   ├── config/
│   │   └── SecurityConfigurations.java
│   └── service/
│       ├── AutenticacaoService.java
│       └── TokenService.java
├── service/                 # Regras de negócio
│   ├── AgendaDeConsultas.java
│   ├── ValidadorAgendamentoDeConsulta.java
│   ├── ValidadorCancelamentoDeConsulta.java
│   ├── springdoc/
│   │   └── SpringDocConfigurations.java
│   └── validations/
│       ├── agendamento/
│       │   ├── ValidadorHorarioAntecedencia.java
│       │   ├── ValidadorHorarioFuncionamentoClinica.java
│       │   ├── ValidadorMedicoAtivo.java
│       │   ├── ValidadorMedicoComOutraConsultaNoMesmoHorario.java
│       │   ├── ValidadorPacienteAtivo.java
│       │   └── ValidadorPacienteSemOutraConsultaNoDia.java
│       └── cancelamento/
│           └── ValidadorHorarioAntecedencia.java
└── exception/               # Tratamento global de exceções
    ├── TratadorDeErros.java
    └── ValidacaoException.java
```

## Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **JDK 17** ou superior
- **Maven 3.8+**
- **MySQL 8.0+**
- **Git** (para clonar o repositório)

## Instalação

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd api
```

2. Crie o banco de dados MySQL:
```sql
CREATE DATABASE vollmed_api;
```

## Configuração

### application.properties

Configure o arquivo `src/main/resources/application.properties`:

```properties
# Nome da aplicação
spring.application.name=api

# Configurações do banco de dados
spring.datasource.url=jdbc:mysql://localhost:3306/vollmed_api
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# Configurações JPA/Hibernate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Configurações de erro
server.error.include-stacktrace=never

# Configuração JWT (usar variável de ambiente em produção)
api.security.token.secret=${JWT_SECRET:sua_chave_secreta}
```

### Variáveis de Ambiente

Para produção, configure as seguintes variáveis de ambiente:

- `JWT_SECRET` - Chave secreta para geração de tokens JWT

## Executando o Projeto

### Modo Desenvolvimento

```bash
mvn spring-boot:run
```

### Executar com DevTools

O Spring DevTools está habilitado para reinicialização automática durante o desenvolvimento.

A aplicação estará disponível em: `http://localhost:8080`

### Documentação da API (Swagger)

A documentação interativa da API está disponível através do Swagger UI:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

A interface do Swagger permite:
- Visualizar todos os endpoints disponíveis
- Testar as requisições diretamente pelo navegador
- Ver os schemas de entrada e saída
- Autenticar usando o token JWT

## Endpoints da API

### Autenticação

#### Login
```http
POST /login
Content-Type: application/json

{
  "login": "usuario@example.com",
  "senha": "senha123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Médicos

Todos os endpoints de médicos requerem autenticação (exceto login).

#### Cadastrar Médico
```http
POST /medicos
Authorization: Bearer {token}
Content-Type: application/json

{
  "nome": "Dr. João Silva",
  "email": "joao.silva@example.com",
  "telefone": "11987654321",
  "crm": "123456",
  "especialidade": "CARDIOLOGIA",
  "endereco": {
    "logradouro": "Rua Exemplo",
    "numero": "100",
    "complemento": "Apto 101",
    "bairro": "Centro",
    "cidade": "São Paulo",
    "uf": "SP",
    "cep": "01310-100"
  }
}
```

#### Listar Médicos (Paginado)
```http
GET /medicos?page=0&size=12&sort=nome,asc
Authorization: Bearer {token}
```

#### Atualizar Médico
```http
PUT /medicos
Authorization: Bearer {token}
Content-Type: application/json

{
  "id": 1,
  "nome": "Dr. João Silva Atualizado",
  "telefone": "11987654322",
  "endereco": {
    "logradouro": "Rua Nova",
    "numero": "200",
    "complemento": "Sala 5",
    "bairro": "Vila Nova",
    "cidade": "São Paulo",
    "uf": "SP",
    "cep": "01310-200"
  }
}
```

#### Excluir Médico (Lógico)
```http
DELETE /medicos/{id}
Authorization: Bearer {token}
```

### Pacientes

#### Cadastrar Paciente
```http
POST /pacientes
Authorization: Bearer {token}
Content-Type: application/json

{
  "nome": "Maria Santos",
  "email": "maria.santos@example.com",
  "telefone": "11987654321",
  "cpf": "12345678900",
  "endereco": {
    "logradouro": "Avenida Exemplo",
    "numero": "500",
    "complemento": "Casa 2",
    "bairro": "Jardim",
    "cidade": "São Paulo",
    "uf": "SP",
    "cep": "02310-100"
  }
}
```

#### Listar Pacientes (Paginado)
```http
GET /pacientes?page=0&size=12&sort=nome,asc
Authorization: Bearer {token}
```

#### Atualizar Paciente
```http
PUT /pacientes
Authorization: Bearer {token}
Content-Type: application/json

{
  "id": 1,
  "nome": "Maria Santos Silva",
  "telefone": "11987654322",
  "endereco": {
    "logradouro": "Avenida Nova",
    "numero": "600",
    "complemento": "Apto 10",
    "bairro": "Jardim Central",
    "cidade": "São Paulo",
    "uf": "SP",
    "cep": "02310-200"
  }
}
```

#### Excluir Paciente (Lógico)
```http
DELETE /pacientes/{id}
Authorization: Bearer {token}
```

### Consultas

#### Agendar Consulta
```http
POST /consultas
Authorization: Bearer {token}
Content-Type: application/json

{
  "idMedico": 1,
  "idPaciente": 1,
  "data": "2026-02-15T10:00:00",
  "especialidade": "CARDIOLOGIA"
}
```

**Resposta:**
```json
{
  "id": 1,
  "idMedico": 1,
  "idPaciente": 1,
  "data": "2026-02-15T10:00:00"
}
```

**Observações:**
- O campo `idMedico` é opcional. Se não informado, o sistema escolhe automaticamente um médico disponível da especialidade indicada
- O campo `especialidade` é obrigatório quando `idMedico` não é informado
- A consulta deve ser agendada com pelo menos 30 minutos de antecedência
- O horário deve estar dentro do funcionamento da clínica (segunda a sábado, 07:00 às 19:00)

#### Cancelar Consulta
```http
DELETE /consultas
Authorization: Bearer {token}
Content-Type: application/json

{
  "idConsulta": 1,
  "motivo": "PACIENTE_DESISTIU"
}
```

**Motivos de cancelamento:**
- `PACIENTE_DESISTIU`
- `MEDICO_CANCELOU`
- `OUTROS`

**Observações:**
- O cancelamento deve ser feito com pelo menos 24 horas de antecedência

## Autenticação

A API utiliza JWT (JSON Web Token) para autenticação. O fluxo é:

1. O cliente faz login enviando credenciais para `/login`
2. O servidor valida as credenciais e retorna um token JWT
3. O cliente inclui o token no header `Authorization` de todas as requisições subsequentes:
   ```
   Authorization: Bearer {token}
   ```
4. O token é validado pelo `SecurityFilter` antes de processar cada requisição

### Configuração de Segurança

- Endpoints públicos: `/login`
- Endpoints protegidos: Todos os demais
- Tempo de expiração do token: 2 horas (configurável)

## Tratamento de Erros

A API possui tratamento centralizado de exceções através da classe `TratadorDeErros`:

### Códigos de Status HTTP

- **200 OK** - Requisição bem-sucedida
- **201 Created** - Recurso criado com sucesso
- **400 Bad Request** - Dados de entrada inválidos
  ```json
  [
    {
      "campo": "email",
      "mensagem": "Email inválido"
    }
  ]
  ```
- **401 Unauthorized** - Falha na autenticação ou credenciais inválidas
- **403 Forbidden** - Acesso negado ao recurso
- **404 Not Found** - Recurso não encontrado
- **500 Internal Server Error** - Erro interno do servidor

### Validações

O projeto utiliza Bean Validation para validar dados de entrada:

- **@NotBlank** - Campo não pode ser vazio
- **@Email** - Validação de formato de email
- **@Pattern** - Validação com expressões regulares
- **@NotNull** - Campo não pode ser nulo
- **@Valid** - Valida objeto aninhado

Mensagens de validação personalizadas estão configuradas em `ValidationMessages.properties`.

## Migrações de Banco de Dados

O Flyway gerencia o versionamento do banco de dados. As migrações estão em `src/main/resources/db/migration/`:

- **V1** - Criação da tabela `medicos`
- **V2** - Adição da coluna `telefone` na tabela `medicos`
- **V3** - Criação da tabela `pacientes`
- **V4** - Adição da coluna `cpf` na tabela `pacientes`
- **V5** - Adição da coluna `telefone` na tabela `pacientes`
- **V6** - Constraint `UNIQUE` na coluna `cpf` da tabela `pacientes`
- **V7** - Adição da coluna `ativo` (TINYINT) na tabela `pacientes`
- **V8** - Adição da coluna `ativo` na tabela `medicos`
- **V9** - Criação da tabela `usuarios`
- **V10** - Criação da tabela `consultas`
- **V11** - Adição da coluna `motivo_cancelamento` na tabela `consultas`

### Executar Migrações Manualmente

```bash
mvn flyway:migrate
```

### Limpar Banco de Dados (Desenvolvimento)

```bash
mvn flyway:clean
```

## Testes

O projeto inclui testes unitários e de integração para garantir a qualidade do código.

### Estrutura de Testes

```
src/test/java/med/voll/api/
├── ApiApplicationTests.java           # Testes de contexto da aplicação
├── controller/
│   ├── ConsultaControllerTest.java    # Testes do controller de consultas
│   └── MedicosControllerTest.java     # Testes do controller de médicos
└── repository/
    └── MedicoRepositoryTest.java      # Testes do repositório de médicos
```

### Tipos de Testes

#### Testes de Controller (@WebMvcTest)
- Testam apenas a camada de controller (slice testing)
- Utilizam `MockMvc` para simular requisições HTTP
- Usam `@MockBean` para isolar dependências de serviços
- Validam status HTTP, headers e corpo das respostas

Exemplo: `ConsultaControllerTest`
```java
@WebMvcTest(ConsultaController.class)
class ConsultaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AgendaDeConsultas agendaDeConsultas;
    
    @Test
    @WithMockUser
    void deveRetornar200QuandoDadosValidos() {
        // teste...
    }
}
```

#### Testes de Repositório (@DataJpaTest)
- Testam queries customizadas do repositório
- Usam banco de dados em memória ou configurado
- Validam comportamento de consultas JPA

Exemplo: `MedicoRepositoryTest`
```java
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository medicoRepository;
    
    @Autowired
    private TestEntityManager em;
    
    @Test
    void deveEscolherMedicoAleatorio() {
        // teste...
    }
}
```

### Executar Testes

```bash
mvn test
```

### Executar Testes com Cobertura

```bash
mvn test jacoco:report
```

### Configuração de Testes

O projeto utiliza o perfil `test` para testes, configurado em `application-test.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vollmed_api_test
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false
```

### Boas Práticas de Testes Implementadas

- **Isolamento**: Testes não dependem uns dos outros
- **Slice Testing**: Uso de `@WebMvcTest` e `@DataJpaTest` para testes focados
- **Mocks**: Uso de `@MockBean` para isolar dependências
- **Nomenclatura Clara**: Nomes de testes descritivos (padrão: `deve...Quando...`)
- **Anotações de Segurança**: Uso de `@WithMockUser` para simular autenticação
- **Validação Completa**: Testes verificam status HTTP, corpo e comportamento esperado

## Build

### Gerar JAR Executável

```bash
mvn clean package
```

O arquivo JAR será gerado em `target/api-0.0.1-SNAPSHOT.jar`

### Executar JAR

```bash
java -jar target/api-0.0.1-SNAPSHOT.jar
```

### Build sem Testes

```bash
mvn clean package -DskipTests
```

## Boas Práticas Implementadas

- **RESTful API** - Seguindo princípios REST
- **DTOs** - Separação entre entidades e objetos de transferência
- **Exclusão Lógica** - Soft delete para manter histórico
- **Paginação** - Listagens paginadas para performance
- **Validação** - Validação robusta de dados de entrada
- **Validações de Negócio** - Validadores específicos para regras de agendamento e cancelamento
- **Strategy Pattern** - Múltiplos validadores aplicados dinamicamente
- **Tratamento de Erros** - Respostas padronizadas de erro
- **Segurança** - Autenticação JWT e proteção de endpoints
- **Documentação Automática** - Swagger/OpenAPI integrado
- **Logs** - Logging estruturado com SLF4J
- **Migrações** - Versionamento de banco de dados com Flyway
- **Injeção de Dependência** - Uso de construtores para DI
- **Testes Automatizados** - Cobertura com testes unitários e de integração
- **Slice Testing** - Testes focados em camadas específicas

## Estrutura de Dados

### Médico
- ID (gerado automaticamente)
- Nome
- Email (único)
- Telefone
- CRM
- Especialidade (ORTOPEDIA, CARDIOLOGIA, GINECOLOGIA, DERMATOLOGIA)
- Endereço (embarcado)
- Ativo (boolean)

### Paciente
- ID (gerado automaticamente)
- Nome
- Email (único)
- Telefone
- CPF (único)
- Endereço (embarcado)
- Ativo (boolean)

### Endereço (Embarcado)
- Logradouro
- Número
- Complemento
- Bairro
- Cidade
- UF
- CEP

### Usuário
- ID (gerado automaticamente)
- Login
- Senha (criptografada)

### Consulta
- ID (gerado automaticamente)
- Médico (relacionamento @ManyToOne)
- Paciente (relacionamento @ManyToOne)
- Data e hora
- Motivo de cancelamento (PACIENTE_DESISTIU, MEDICO_CANCELOU, OUTROS)

## Contribuição

Contribuições são bem-vindas! Para contribuir:

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

### Padrões de Código

- Seguir convenções Java
- Utilizar Lombok quando apropriado
- Adicionar validações necessárias
- Documentar código complexo
- Escrever testes para novas funcionalidades

## Aprendizados

Este projeto foi desenvolvido focando em:

- Desenvolvimento de APIs REST com Spring Boot
- Boas práticas de desenvolvimento Java
- Persistência de dados com JPA/Hibernate
- Versionamento de banco de dados com Flyway
- Validação de dados com Bean Validation
- Implementação de regras de negócio complexas
- Padrão Strategy para validações
- Segurança com Spring Security e JWT
- Tratamento de exceções e respostas HTTP
- Documentação de APIs com Swagger/OpenAPI
- Testes automatizados (unitários e de integração)
- Arquitetura em camadas
- Princípios SOLID
- Relacionamentos JPA (@ManyToOne, @Embeddable)
