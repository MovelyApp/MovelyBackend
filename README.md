# Movely — Backend

Sistema de gamificação de hábitos saudáveis em grupo - os usuários se reúnem em grupos, criam desafios com metas específicas (água, passos, sono, treino, estudo), registram suas atividades diárias e competem em rankings baseados em pontuação.

Projeto final da disciplina **Arquitetura de Objetos** (Insper, 2026).

## Membros
Carolina Amorim, Lara Abduni, Gabrielli Araujo, Rafaela Coutinho, Robson França

## Tecnologias

- **Java 21**
- **Spring Boot 3.5**
- **Spring Data JPA** (persistência)
- **Spring Web** (API REST)
- **Spring Security + JWT** (autenticação)
- **H2 Database** (banco em memória para desenvolvimento)
- **Lombok** (redução de boilerplate)
- **Maven** (build)

## Arquitetura

O projeto segue uma arquitetura em camadas com separação clara de responsabilidades:

```
br.movely.movelyapp
├── config/         configurações do Spring (CORS, segurança, JWT)
├── controller/     endpoints REST que recebem as requisições HTTP
├── dto/            objetos de transferência entre API e cliente
├── model/          entidades de domínio (JPA)
│   └── enums/      enums do domínio (ex: SleepQuality)
├── repository/     interfaces JpaRepository para acesso ao banco
└── service/        regras de negócio e orquestração
```

Cada camada tem um papel:

- **Controller** valida entrada HTTP, delega para o service e formata a resposta. Não contém regras de negócio.
- **Service** contém as regras de negócio e coordena chamadas aos repositories.
- **Repository** abstrai o acesso ao banco por meio de interfaces do Spring Data JPA.
- **Model** representa o domínio. As regras de cálculo de progresso e pontuação ficam nas próprias entidades, não nos services, porque dependem do estado interno de cada Goal (princípio "Tell, Don't Ask").

## Domínio

O sistema é organizado em torno de cinco conceitos principais:

**Usuário e Grupo** — usuários se cadastram e participam de grupos por meio da entidade associativa `GrupoUsuario`, que registra o papel (`PapelGrupo`) e a data de entrada de cada membro. Um grupo pode ter múltiplos usuários e um usuário pode pertencer a múltiplos grupos.

**Desafio** — cada grupo cria desafios com data de início e fim. Um desafio é um conjunto de metas que os membros do grupo precisam cumprir no período.

**Goal (Meta)** — entidade abstrata que representa uma meta dentro de um desafio. Cinco subclasses concretas implementam tipos específicos de meta: `WaterGoal`, `StepsGoal`, `SleepGoal`, `WorkoutGoal` e `StudyGoal`. Cada subclasse tem seu próprio campo de objetivo e implementa as fórmulas de progresso e pontuação polimorficamente.

**Registro** — entidade abstrata que representa o log diário de atividade de um usuário. Subclasses concretas (`RegistroAgua`, `RegistroPassos`, `RegistroSono`, `RegistroTreino`, `RegistroEstudo`) armazenam os dados específicos de cada tipo de atividade.

**Ranking** — para cada grupo, um ranking é mantido com a pontuação acumulada de cada usuário. Cada `RankingEntry` representa a posição e pontuação de um membro.

## Modelagem orientada a objetos

### Herança e polimorfismo

Tanto `Goal` quanto `Registro` usam herança para representar variações de um mesmo conceito. A estratégia JPA escolhida foi `SINGLE_TABLE`: todas as subclasses compartilham uma tabela única, com uma coluna discriminadora (`goal_type`, `registro_type`) indicando o tipo concreto de cada linha.

A escolha por `SINGLE_TABLE` privilegia simplicidade e performance em queries polimórficas — adequada para o escopo do projeto. As alternativas (`JOINED` ou `TABLE_PER_CLASS`) trariam mais normalização, mas com custo de complexidade e overhead de joins.

### Cálculo de progresso e pontuação

A classe abstrata `Goal` declara dois métodos abstratos:

```java
public abstract double calculateProgress(Map<String, Double> values);
public abstract double calculatePoints(Map<String, Double> values);
```

Cada subclasse implementa sua fórmula. O retorno de `calculateProgress` é normalizado entre 0 e 1 (porcentagem para a UI), enquanto `calculatePoints` pode acumular além de 100 quando o usuário supera a meta — incentivando a superação.

As regras de pontuação por tipo de meta são:

- **Água, passos, estudo:** proporcional ao consumido sobre o objetivo, multiplicado por 100. Acumula sem teto.
- **Treino:** 100 pontos fixos por treino registrado.
- **Sono:** proporcional às horas dormidas mais um bônus de qualidade que varia de -20 (péssimo) a +20 (excelente), modelado como enum `SleepQuality`.

### Encapsulamento

Os campos das entidades são privados e expostos via getters/setters do Lombok. Regras de domínio (cálculos, validações) ficam dentro das entidades, não dispersas nos services. Métodos como `Desafio.encerrarDesafio()` e `Grupo.atualizarInfo()` aplicam o princípio "Tell, Don't Ask".

## API REST

Os principais endpoints estão organizados por recurso:

```
POST   /api/auth/register           cadastro de usuário
POST   /api/auth/login              login (retorna JWT)

GET    /api/goals                   lista todas as metas
GET    /api/goals/{id}              busca meta por ID
GET    /api/goals/challenge/{id}    lista metas de um desafio
DELETE /api/goals/{id}              remove uma meta

POST   /api/goals/water             cria meta de água
POST   /api/goals/steps             cria meta de passos
POST   /api/goals/sleep             cria meta de sono
POST   /api/goals/workout           cria meta de treino
POST   /api/goals/study             cria meta de estudo
```

Endpoints de grupos, desafios, registros e rankings seguem o mesmo padrão.

## Como executar

### Pré-requisitos

- JDK 21 ou superior
- Maven 3.8+ (ou usar o wrapper `./mvnw` incluso)

### Rodando localmente

```bash
git clone <url-do-repo>
cd MovelyBackend
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

### Console do banco H2

Durante o desenvolvimento, o banco H2 em memória pode ser inspecionado em:

```
http://localhost:8080/h2-console
```

Credenciais:

- **JDBC URL:** `jdbc:h2:mem:movelydb`
- **User:** `sa`
- **Password:** (vazio)

O banco é recriado a cada inicialização da aplicação.

## Estrutura do projeto

```
MovelyBackend/
├── src/
│   ├── main/
│   │   ├── java/br/movely/movelyapp/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── MovelyappApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```