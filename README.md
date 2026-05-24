# 💰 FinanceApp — API REST

API REST completa para gerenciamento de finanças pessoais, desenvolvida com **Java 21** e **Spring Boot 3**.

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 3.5**
- **Spring Security** — autenticação e autorização
- **JWT (JSON Web Token)** — tokens de acesso stateless
- **Spring Data JPA / Hibernate** — persistência de dados
- **MySQL** — banco de dados relacional
- **Maven** — gerenciamento de dependências
- **Lombok** — redução de boilerplate

## ✨ Funcionalidades

- ✅ Cadastro e autenticação de usuários com senha criptografada (BCrypt)
- ✅ Geração e validação de tokens JWT
- ✅ CRUD completo de transações financeiras
- ✅ Categorização de receitas e despesas
- ✅ Resumo financeiro: total de receitas, despesas e saldo
- ✅ Filtro de transações por tipo
- ✅ Rotas protegidas com autenticação via Bearer Token

## 📐 Arquitetura

O projeto segue o padrão de camadas MVC:

```
Controller → Service → Repository → Model
```

```
src/
├── controller/       # Endpoints da API (AuthController, TransacaoController)
├── service/          # Regras de negócio (UsuarioService, TransacaoService)
├── repository/       # Acesso ao banco de dados (JPA Repositories)
├── model/            # Entidades (Usuario, Transacao, TipoTransacao)
├── dto/              # Objetos de transferência (LoginRequest, TransacaoRequest...)
└── security/         # JWT, Filtros e configuração do Spring Security
```

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** para autenticação stateless:

1. O usuário realiza login com email e senha
2. A API valida as credenciais e retorna um token JWT
3. O token deve ser enviado no header de todas as requisições protegidas:
```
Authorization: Bearer {token}
```

## 📡 Endpoints

### Autenticação
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/auth/register` | Cadastro de novo usuário |
| POST | `/api/auth/login` | Login e geração de token |

### Transações (requer autenticação)
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/transacoes` | Listar todas as transações |
| POST | `/api/transacoes` | Criar nova transação |
| PUT | `/api/transacoes/{id}` | Atualizar transação |
| DELETE | `/api/transacoes/{id}` | Deletar transação |
| GET | `/api/transacoes/resumo` | Resumo financeiro (receitas, despesas, saldo) |

### Exemplo de requisição — Criar transação
```json
POST /api/transacoes
Authorization: Bearer {token}

{
  "descricao": "Salário",
  "valor": 3000.00,
  "data": "2026-05-01",
  "tipo": "RECEITA",
  "categoria": "Trabalho"
}
```

### Exemplo de resposta — Resumo financeiro
```json
{
  "totalReceitas": 3000.00,
  "totalDespesas": 500.00,
  "saldo": 2500.00
}
```

## ⚙️ Como rodar localmente

### Pré-requisitos
- Java 21+
- Maven 3.9+
- MySQL 8.0+

### Passos

1. Clone o repositório:
```bash
git clone https://github.com/LMirandaC07/financeiro-api.git
cd financeiro-api
```

2. Crie o banco de dados:
```sql
CREATE DATABASE financeiro;
```

3. Configure o `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/financeiro
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

4. Execute o projeto:
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

## 🌐 Deploy

A API está deployada no **Railway**:
```
https://financeiro-api-production-aa9b.up.railway.app
```

## 🔗 Front-end

O front-end desta API está disponível em:
- Repositório: [financeiro-front](https://github.com/LMirandaC07/financeiro-front)
- Deploy: [financeiro-front-azure.vercel.app](https://financeiro-front-azure.vercel.app)

---

Desenvolvido por **Luis Miranda** · [LinkedIn](https://www.linkedin.com/in/gustavomirandac/)
