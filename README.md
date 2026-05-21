# 🌧️ Análise de Precipitação Pluviométrica — Acaraú/CE 2025

Projeto Java com Spring Boot para análise estatística dos dados de precipitação
diária da cidade de **Acaraú - CE** referentes ao ano de **2025**, com dados
fornecidos pela [FUNCEME](http://www.funceme.br/?page_id=2694).

---

## 📋 Funcionalidades

- ✅ Leitura e parsing de arquivo CSV
- ✅ Total de precipitação por mês
- ✅ Média diária de precipitação anual
- ✅ Média diária de precipitação por mês
- ✅ Dia de maior precipitação do ano
- ✅ Dia de menor precipitação do ano (excluindo dias sem chuva)
- ✅ Mês de maior precipitação total
- ✅ Mês de menor precipitação total
- ✅ Ranking dos 10 dias mais chuvosos do ano
- ✅ Métodos parametrizados por ano, mês e quantidade

## 🏗️ Arquitetura

```
src/
└── main/
    ├── java/br/funceme/
    │   ├── model/
    │   │   └── RegistroChuva.java
    │   ├── repository/
    │   │   └── PluviometriaRepository.java
    │   ├── service/
    │   │   └── PluviometriaService.java
    │   ├── util/
    │   │   └── RelatorioFormatter.java
    │   └── Main.java
    └── resources/
        ├── application.properties
        └── pluviometria_acarau_2025.csv
```

### Separação de Responsabilidades

| Camada | Classe | Responsabilidade |
|--------|--------|-----------------|
| **model** | `RegistroChuva` | Representa um registro diário de chuva |
| **repository** | `PluviometriaRepository` | Lê e filtra os dados do CSV |
| **service** | `PluviometriaService` | Calcula todas as estatísticas |
| **util** | `RelatorioFormatter` | Formata e exibe os resultados |
| **main** | `Main` | Orquestra a execução via Spring Boot |

### Estruturas de Dados utilizadas (Java Collections API)

| Estrutura | Uso |
|-----------|-----|
| `List<RegistroChuva>` | Armazena os registros do CSV |
| `ArrayList<>` | Implementação para ordenação e acesso por índice |
| `TreeMap<Integer, Double>` | Totais e médias por mês (chaves ordenadas 1→12) |
| `Optional<RegistroChuva>` | Retorno seguro de maior/menor precipitação |

---

## 🚀 Tecnologias

- Java 17+
- Spring Boot 3.5.14
- Maven
- JUnit 5
- API Collections do Java
- Git e GitHub


## 📄 Fonte dos Dados

| Campo | Valor |
|-------|-------|
| **Cidade** | Acaraú, Ceará, Brasil |
| **Ano** | 2025 |
| **Fonte** | [FUNCEME](http://www.funceme.br/?page_id=2694) |
| **Unidade** | milímetros (mm) |
