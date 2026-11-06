# QuickHelp — Backend API

API REST em **Spring Boot** para o projeto **QuickHelp**, uma solução de apoio e acionamento de emergência voltada a usuárias em situação de risco.

Este repositório concentra o **backend** da aplicação: cadastros, chamados SOS, localização, preferências, autenticação básica e documentação interativa via **Swagger**.

---

## Sumário

- [Sobre o projeto](#sobre-o-projeto)
- [Dor da usuária e como resolvemos](#dor-da-usuária-e-como-resolvemos)
- [Base do desenvolvimento](#base-do-desenvolvimento)
- [Tecnologias](#tecnologias)
- [Estrutura do projeto](#estrutura-do-projeto)
- [O que foi implementado](#o-que-foi-implementado)
- [Como rodar](#como-rodar)
- [Swagger — demonstração para avaliação](#swagger--demonstração-para-avaliação)
- [Contas de teste](#contas-de-teste)
- [Principais endpoints](#principais-endpoints)
- [Participantes](#participantes)

---

## Sobre o projeto

O **QuickHelp** nasceu da necessidade de oferecer um canal rápido e discreto para pedir ajuda, organizar contatos de confiança e registrar informações relevantes em momentos críticos.

O backend foi construído como uma API REST completa, com:

- CRUD das entidades principais
- Login com senha armazenada em hash (BCrypt)
- Acompanhamento de chamados SOS
- Registro de localização durante o atendimento
- Preferências da usuária (tema, modo disfarçado e permissão de GPS)
- Banco H2 em memória com dados iniciais para demonstração
- Swagger UI para testar os endpoints sem precisar de front

---

## Dor da usuária e como resolvemos

### A dor

Em situações de risco, a usuária precisa:

- pedir ajuda **com agilidade**
- acionar o pedido de forma **discreta**, quando necessário
- ter **contatos de emergência** já cadastrados
- permitir que alguém acompanhe **onde ela está**
- manter preferências de interface que aumentem a segurança (ex.: modo disfarçado)

Sem um sistema estruturado, esses dados ficam dispersos e o atendimento perde tempo e contexto.

### Como o backend resolve

| Necessidade | Como a API atende |
|---|---|
| Conta e acesso | Cadastro e login de usuária / backoffice |
| Rede de apoio | CRUD de contatos de emergência |
| Pedido de socorro | Abertura e acompanhamento de `ChamadoSOS` |
| Discrição | Acionamento `BOTAO_SOS` ou `DISFARCADO` |
| Localização | Histórico e última posição do chamado |
| Preferências de segurança | Tema, modo disfarçado e permissão de localização |
| Atendimento | Backoffice altera status (`ABERTO` → `EM_ATENDIMENTO` → `ENCERRADO`) |
| Canal de suporte | Mensagens enviadas pelo formulário do site |

Assim, o backend organiza o fluxo completo: **cadastro → preferências → contatos → SOS → localização → atendimento**.

---

## Base do desenvolvimento

O desenvolvimento seguiu duas referências principais:

1. **Documento de definição inicial das entidades do QuickHelp**  
   Serviu como base funcional: atributos, relacionamentos, enums e entidades previstas (usuária, endereço, contato, chamado SOS, mensagem, localização, preferências etc.).

2. **Projeto acadêmico `projeto-musica`**  
   Serviu como base técnica de estrutura e padrão de código:
   - organização de pastas (`model`, `control`, `repository`, `projection`, `swagger`)
   - convenção de endpoints (`/todas`, `/nova`, `/{id}/atualizar`, `/{id}/remover`)
   - uso de H2 + `import.sql`
   - documentação OpenAPI / Swagger
   - buscas “não otimizada”, HQL e SQL nativo com projection

A partir disso, o grupo adaptou o domínio musical para o domínio de emergência do QuickHelp e adicionou funcionalidades específicas (login com hash, status do SOS, localização e preferências).

---

## Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 17 | Linguagem |
| Spring Boot 4.1.1 | Framework da API |
| Spring Data JPA | Persistência |
| H2 Database | Banco em memória para aula/demo |
| springdoc OpenAPI | Swagger UI |
| BCrypt | Hash de senha |
| Maven Wrapper | Build e execução sem instalar Maven global |

---

## Estrutura do projeto

```text
quickhelp/
├── pom.xml
├── mvnw / mvnw.cmd
├── README.md
└── src/main/java/br/com/fatecmaua/quickhelp/
    ├── QuickhelpApplication.java
    ├── control/        # Controllers REST
    ├── model/          # Entidades e Enums
    ├── repository/     # Acesso a dados
    ├── projection/     # Projeções SQL
    └── swagger/        # Configuração OpenAPI
```

Recursos:

```text
src/main/resources/
├── application.properties
└── import.sql
```

---

## O que foi implementado

### Entidades

| Entidade | Descrição |
|---|---|
| `Usuario` | Conta da usuária ou da equipe de backoffice |
| `Endereco` | Endereços vinculados à usuária |
| `ContatoEmergencia` | Pessoas para receber alertas |
| `ChamadoSOS` | Pedido de socorro e seu ciclo de vida |
| `MensagemContato` | Dúvidas/sugestões do formulário do site |
| `LocalizacaoChamado` | Coordenadas durante o SOS |
| `PreferenciaUsuario` | Tema, modo disfarçado e GPS |

### Enums

- `PerfilUsuarioEnum`: `USUARIA`, `BACKOFFICE`
- `StatusChamadoEnum`: `ABERTO`, `EM_ATENDIMENTO`, `ENCERRADO`, `CANCELADO`
- `AcionamentoEnum`: `BOTAO_SOS`, `DISFARCADO`
- `TemaEnum`: `CLARO`, `ESCURO`

### Diferenciais além do CRUD

- Login com validação de senha (`POST /usuarios/login`)
- Senha nunca retornada na resposta JSON
- Atualização parcial de status do chamado e de tema
- Busca de chamados por status em três estratégias (laço, HQL e SQL + projection)
- Endpoint da última localização do chamado
- Preenchimento automático de `encerradoEm`
- Tratamento de conflitos (e-mail duplicado, exclusão com vínculos)

> Entidades previstas no documento e **não** implementadas nesta entrega: `NotificacaoEmergencia`, `ConteudoApoio` e `RegistroProva`.

---

## Como rodar

### Pré-requisitos

- JDK 17 ou superior
- Porta `8080` livre
- (Opcional) Eclipse / STS para importar como projeto Maven

### Via terminal (recomendado)

No Windows (PowerShell ou CMD):

```bash
cd quickhelp-backend
.\mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
cd quickhelp-backend
./mvnw spring-boot:run
```

Aguarde a mensagem:

```text
Started QuickhelpApplication
```

### Via Eclipse

1. **File → Import → Existing Maven Projects**
2. Selecione a pasta do repositório
3. Execute a classe `QuickhelpApplication`

### URLs úteis

| Recurso | URL |
|---|---|
| API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| H2 Console | http://localhost:8080/h2-console |

**H2 Console**

- JDBC URL: `jdbc:h2:mem:quickhelp`
- Usuário: `sa`
- Senha: *(vazia)*

### Se a porta 8080 estiver ocupada

```powershell
Get-NetTCPConnection -LocalPort 8080 | ForEach-Object { taskkill /F /PID $_.OwningProcess }
```

Depois execute novamente `.\mvnw.cmd spring-boot:run`.

---

## Swagger — demonstração para avaliação

1. Suba a aplicação.
2. Abra http://localhost:8080/swagger-ui/index.html
3. Expanda os grupos de endpoints (Autenticação, Inserção, Retorno etc.).
4. Clique em **Try it out**, preencha o body/parâmetros e execute **Execute**.

### Roteiro sugerido de demonstração

1. **Login** — `POST /usuarios/login`
2. **Listar chamados** — `GET /chamados/todas`
3. **Filtrar abertos** — `GET /chamados/status-otimizada-2?status=ABERTO`
4. **Abrir SOS** — `POST /chamados/nova`
5. **Consultar última localização** — `GET /localizacoes/chamado/1/ultima`
6. **Atualizar status** — `PATCH /chamados/{id}/status?status=EM_ATENDIMENTO`

Exemplo de login:

```json
{
  "email": "maria.souza@email.com",
  "senha": "123456"
}
```

Exemplo de novo chamado:

```json
{
  "usuario": { "id": 1 },
  "modoAcionamento": "BOTAO_SOS",
  "responsavel": { "id": 2 }
}
```

---

## Contas de teste

Os dados iniciais são carregados automaticamente pelo `import.sql`.

| E-mail | Senha | Perfil |
|---|---|---|
| `maria.souza@email.com` | `123456` | USUARIA |
| `ana.ribeiro@quickhelp.com` | `123456` | BACKOFFICE |

> O banco H2 é em memória: a cada reinício da aplicação, os dados seed são recriados.

---

## Principais endpoints

Convenção geral:

- `GET /todas`
- `GET /{id}`
- `POST /nova`
- `PUT /{id}/atualizar`
- `DELETE /{id}/remover`

| Recurso | Base |
|---|---|
| Usuários | `/usuarios` |
| Endereços | `/enderecos` |
| Contatos | `/contatos` |
| Chamados SOS | `/chamados` |
| Mensagens | `/mensagens` |
| Localizações | `/localizacoes` |
| Preferências | `/preferencias` |

Endpoints especiais:

| Método | Endpoint | Função |
|---|---|---|
| POST | `/usuarios/login` | Autenticação |
| PATCH | `/chamados/{id}/status` | Atualiza status do SOS |
| GET | `/chamados/status-otimizada-1` | Busca HQL por status |
| GET | `/chamados/status-otimizada-2` | Busca SQL + projection |
| GET | `/localizacoes/chamado/{id}/ultima` | Última posição |
| PATCH | `/preferencias/{id}/tema` | Atualiza tema |

---

## Participantes

- Gustavo Pereira dos Santos  
- Gabriele Brito  
- Amanda Godinho  
- Pedro Henrique  
- Miguel Fabiani  
- Felipe Dominicheli  

---

## Observações para a banca

- Este repositório é o **backend**. O front (PHP ou outro) consome estes endpoints via HTTP.
- A documentação interativa oficial para testes é o **Swagger**.
- O projeto segue o padrão acadêmico visto em aula e aplica o domínio definido no documento de entidades do QuickHelp.
