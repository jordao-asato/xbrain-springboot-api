# Desafio Técnico - API de Vendas 

API RESTful desenvolvida em Java e Spring Boot para gerenciamento e cálculo de estatísticas de vendas, utilizando banco de dados em memória (H2). 

## Tecnologias e Ferramentas
* **Java** (utilizado: 25)
* **Spring Boot** (Web, Data JPA)
* **H2 Database** (Banco de dados em memória)
* **Postman** (Teste e monitoramento da API)
* **Maven** (Gerenciador de dependências)
* **JUnit 5 & Mockito** (Testes automatizados)

### Sugestão: Utilizar o Spring Tool Suite (SpringTools for Eclipse) para rodar
### Sugestão 2: O Postman facilita bastante os testes! 

## Como Executar o Projeto Localmente

1. Clone este repositório:
   ```bash
   git clone https://github.com/jordao-asato/xbrain-springboot-api.git

2. Acesse a pasta corretamente:
   ```bash
   cd xbrain-springboot-api
3. Execute o projeto usando o Maven Wrapper (não é necessário ter o Maven instalado na máquina):
   ```bash
   ./mvnw spring-boot:run

### A aplicação estará disponível em: http://localhost:8080

## Como rodar os testes
  Para rodar a suíte de testes unitários e de integração desenvolvidos para a camada de serviços:
  ```bash
  ./mvnw test
  ```

## Endpoints da API

1. Criar uma nova Venda
   * Rota: POST/sales
   * Corpo da requisição (JSON):
     ```bash
     {
      "saleDate": "2026-04-01",
      "amount": 350.50,
      "sellerId": 1
     }
     ```
2. Obter Estatísticas de Vendas (Total e Média Diária)
  * Rota: GET /sales/estatisticas
  * Parâmetros: dataInicio (YYYY-MM-DD) e dataFim (YYYY-MM-DD)
  * Exemplo de Requisição: http://localhost:8080/sales/estatisticas?dataInicio=2026-03-29&dataFim=2026-04-01
