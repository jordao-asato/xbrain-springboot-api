# SalesTrack — API REST de Gestão de Vendas

[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?logo=junit5)](https://junit.org/junit5/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

> Desafio técnico proposto pela **X-Brain** — API REST para registro de vendas e geração de estatísticas por vendedor, com testes unitários escritos com JUnit 5 e Mockito.

---

## Sobre o Projeto

O **SalesTrack** é uma API REST desenvolvida com Spring Boot 4 e JPA/Hibernate para gerenciar vendas e calcular indicadores de desempenho por vendedor. O endpoint de estatísticas consolida, para um período informado, o **total vendido** e a **média diária de vendas** por vendedor — agrupando as vendas via Java Streams e calculando a média com precisão usando `BigDecimal` e `RoundingMode.HALF_UP`. A camada de serviço é coberta por uma suíte de **4 testes unitários** com JUnit 5 e Mockito, validando os cenários de sucesso e os casos de erro esperados.

---

## Endpoints da API

### Vendas `/sales`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/sales` | Lista todas as vendas |
| `GET` | `/sales/{id}` | Busca venda por ID |
| `POST` | `/sales` | Registra uma nova venda |
| `GET` | `/sales/estatisticas` | Retorna estatísticas por vendedor no período |

### Vendedores `/sellers`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/sellers` | Lista todos os vendedores |
| `GET` | `/sellers/{id}` | Busca vendedor por ID |

---

## Endpoint de Estatísticas

`GET /sales/estatisticas?dataInicio={YYYY-MM-DD}&dataFim={YYYY-MM-DD}`

Retorna, para cada vendedor com vendas no período, o total acumulado e a média diária calculada sobre o número de dias do intervalo (inclusivo).

**Exemplo de requisição:**
```
GET /sales/estatisticas?dataInicio=2026-03-29&dataFim=2026-04-01
```

**Exemplo de resposta:**
```json
[
  {
    "name": "Sergio",
    "totalSales": 650.00,
    "avgDailySales": 162.50
  },
  {
    "name": "Fernanda",
    "totalSales": 600.00,
    "avgDailySales": 150.00
  }
]
```

> A média diária é calculada como `totalSales / númeroDeDias`, onde `númeroDeDias = dataFim - dataInicio + 1` (ambas as datas inclusas).

---

## Modelo de Domínio

```
Seller (tb_seller)
  │  id, name
  │
  └──── OneToMany ────► Sale (tb_sale)
                          id, saleDate, amount, seller_id (FK)
```

### DTOs

| DTO | Uso |
|---|---|
| `SaleRequestDTO` | Corpo do `POST /sales` — recebe `saleDate`, `amount`, `sellerId` |
| `SaleResponseDTO` | Resposta do `POST /sales` — inclui dados do vendedor vinculado |
| `SellerStatsDTO` | Resposta do endpoint de estatísticas — `name`, `totalSales`, `avgDailySales` |

---

## Testes Unitários

A camada de serviço (`SaleService`) é coberta por 4 testes com **JUnit 5 + Mockito**, isolando o serviço com mocks dos repositórios:

| Teste | Cenário |
|---|---|
| `insert_DeveCriarVendaQuandoVendedorExiste` | Criação bem-sucedida de venda com vendedor válido |
| `insert_DeveLancarExcecao_QuandoVendedorNaoExiste` | Lança `IllegalArgumentException` para `sellerId` inexistente |
| `getSellerStatistics_DeveCalcularMatematicaCorreta` | Valida total (R$ 300,00) e média diária (R$ 150,00) para 2 dias |
| `getSellerStatistics_DeveLancarExcecao_QuandoDatasInvalidas` | Lança exceção quando `dataInicio` é posterior a `dataFim` |

Para rodar a suíte:

```bash
./mvnw test
```

---

## Arquitetura

```
resources/          ← Controllers REST (@RestController)
services/           ← Regras de negócio e cálculo de estatísticas (@Service)
repositories/       ← Spring Data JPA (@Repository)
entities/           ← Entidades JPA (Seller, Sale)
dto/                ← Records Java para entrada/saída de dados
config/             ← Seed de dados para o perfil de teste (TestConfig)
```

---

## Tecnologias

- **Java 25**
- **Spring Boot 4.0.5** (Spring MVC, Spring Data JPA)
- **Hibernate** — ORM e geração de DDL
- **H2 Database** — banco em memória para o perfil `test`
- **JUnit 5 + Mockito** — testes unitários da camada de serviço
- **Java Records** — DTOs imutáveis com `record`
- **BigDecimal + RoundingMode** — precisão em cálculos financeiros
- **Maven** — gerenciamento de dependências e build

---

## Como Executar

### Pré-requisitos

- Java 25+

> O Maven Wrapper (`mvnw`) já está incluído no projeto — não é necessário ter o Maven instalado.

### Rodar a aplicação

```bash
# Clonar o repositório
git clone https://github.com/jordao-asato/salestrack.git
cd salestrack

# Rodar com Maven Wrapper
./mvnw spring-boot:run
```

A aplicação sobe com o perfil `test` ativado por padrão, utilizando banco H2 em memória e populando automaticamente os dados de seed (2 vendedores e 3 vendas).

**Base URL:** `http://localhost:8080`

### Console H2 (banco em memória)

```
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
User:     sa
Password: (vazio)
```

### Rodar os testes

```bash
./mvnw test
```

---

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/workshop/xbrainvendas/
│   │   ├── XbrainvendasApplication.java
│   │   ├── config/
│   │   │   └── TestConfig.java              # Seed de dados (perfil test)
│   │   ├── dto/
│   │   │   ├── SaleRequestDTO.java           # Record de entrada para POST /sales
│   │   │   ├── SaleResponseDTO.java          # Record de resposta do POST /sales
│   │   │   └── SellerStatsDTO.java            # Record de resposta das estatísticas
│   │   ├── entities/
│   │   │   ├── Sale.java
│   │   │   └── Seller.java
│   │   ├── repositories/
│   │   │   ├── SaleRepository.java            # findBySaleDateBetween (query derivada)
│   │   │   └── SellerRepository.java
│   │   ├── resources/
│   │   │   ├── SaleResource.java
│   │   │   └── SellerResource.java
│   │   └── services/
│   │       ├── SaleService.java               # Lógica de inserção e estatísticas
│   │       └── SellerService.java
│   └── resources/
│       ├── application.properties             # Perfil ativo: test
│       └── application-test.properties        # Config H2
└── test/
    └── java/com/workshop/xbrainvendas/
        └── services/
            └── SaleServiceTest.java            # 4 testes unitários com JUnit 5 + Mockito
```

---

## Autor

**Jordão Asato**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Jordão%20Asato-blue?logo=linkedin)](https://www.linkedin.com/in/jordao-asato-327063385)
