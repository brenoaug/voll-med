# Voll.med API

Projeto de estudos de uma API REST desenvolvida com Spring Boot para gerenciamento de médicos e pacientes de uma clínica médica.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.9**
- **Spring Data JPA** - Persistência de dados
- **MySQL** - Banco de dados
- **Flyway** - Versionamento do banco de dados
- **Lombok** - Redução de código boilerplate
- **Bean Validation** - Validação de dados
- **Maven** - Gerenciamento de dependências

## Funcionalidades Implementadas

### Médicos
- Cadastro de médicos com dados pessoais, especialidade e endereço
- Especialidades suportadas: Ortopedia, Cardiologia, Ginecologia e Dermatologia

### Pacientes
- Cadastro de pacientes com dados pessoais e endereço
- CPF único por paciente
- Telefone de contato

## Estrutura do Banco de Dados

O projeto utiliza Flyway para versionamento do banco de dados com as seguintes migrações:

- `V1` - Criação da tabela de médicos
- `V2` - Adição do campo telefone na tabela de médicos
- `V3` - Criação da tabela de pacientes
- `V4` - Adição do campo CPF na tabela de pacientes
- `V5` - Adição do campo telefone na tabela de pacientes
- `V6` - Constraint UNIQUE no CPF dos pacientes

## Como Executar

1. Configure o banco de dados MySQL
2. Configure as credenciais no arquivo `application.properties`
3. Execute a aplicação:
```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## Aprendizados

Projeto focado no aprendizado de:
- Desenvolvimento de APIs REST
- Boas práticas com Spring Boot
- Persistência de dados com JPA/Hibernate
- Versionamento de banco de dados com Flyway
- Validação de dados com Bean Validation

