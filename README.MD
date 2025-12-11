## ☀️ Weather API Wrapper Service

![Java](https://img.shields.io/badge/Java-17+-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![Redis](https://img.shields.io/badge/Redis-Cache-red)
![Maven](https://img.shields.io/badge/Build-Maven-orange)

Um serviço wrapper em **Spring Boot** para consultar dados de clima de uma API externa, utilizando **Redis** para caching eficiente dos resultados.

### 🌟 Visão Geral

Este projeto é uma API RESTful simples que serve como intermediária (wrapper) para uma API de previsão do tempo externa. Ele é construído em Java com Spring Boot e implementa caching usando Spring Cache Abstraction com **Redis** como o provedor de cache. Isso melhora a performance e reduz o número de chamadas repetidas à API externa.

### 🚀 Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot**
* **Spring Web**
* **Spring Data Redis**
* **Redis** (como Cache Provider)
* **Maven**
* **Docker** (para o Redis)
* **Jackson** (para processamento JSON)
* **Lombok** (para a `Weather` class)

### 🛠️ Configuração e Pré-requisitos

Para rodar este projeto, você precisará dos seguintes pré-requisitos:

1.  **JDK (Java Development Kit)**: Versão 17 ou superior.
2.  **Maven**: Para gerenciar as dependências.
3.  **Docker**: Para rodar o container do Redis.
4.  **Chave de API**: Uma chave válida para a API de previsão do tempo (necessária para a propriedade `${API_KEY}`).

#### 1. Configurando o Redis com Docker

O Redis é usado para armazenar os dados de cache. Execute o seguinte comando para iniciar um container Docker do Redis na porta padrão (`6379`):

```bash
docker run --name my-redis -p 6379:6379 -d redis
```

💡 **Nota:** Se você parar o container, o cache será perdido. Você pode iniciá-lo novamente usando docker start my-redis.

---

### 2. Configurando a Chave de API

O projeto utiliza a API Visual Crossing (https://www.visualcrossing.com/weather-api/) para obter os dados de clima. A chave de API (API_KEY) e a URL base (app.api.url) são lidas do ambiente ou do application.properties.

Para definir a chave de API como variável de ambiente (exemplo para PowerShell no Windows):

```powershell
$env:API_KEY = "SUA CHAVE"
```

Substitua "SUA CHAVE" pela sua chave de API real. O WeatherController.java injeta essa chave usando @Value("${API_KEY}").

## 🏃 Como Executar o Projeto

1. Clone o repositório:
```bash
git clone <URL do seu repositório>
```

2. Entrar na pasta do projeto:
```bash
cd weather_API
```

3. Verifique se o Redis está rodando (conforme a seção anterior).

4. Execute a aplicação Spring Boot usando o Maven Wrapper:
* Linux / MacOS:
```bash
./mvnw spring-boot:run
```
* Windows:
```bash
.\mvnw spring-boot:run
```

**A aplicação estará disponível em http://localhost:8080.**

## 💻 Endpoint

O serviço expõe um único endpoint GET para buscar a previsão do tempo:

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| **GET** | `/weather/{país_ou_cidade}` | Retorna a previsão do tempo atual, máxima e mínima para o local especificado. |

* **Exemplo de Uso (pesquisa):**

```aiignore
http://localhost:8080/weather/{cidade ou pais}
```

* **Exemplo de Retorno (JSON):**
```Json
{
    "dateTime": "2023-10-27",
    "description": "Clear skies throughout the day.",
    "address": "sao paulo",
    "temperature": 25.0,
    "tempmax": 28.0,
    "tempmin": 19.0
}
```

## 🧠 Detalhes da Implementação (Caching com Redis)

O uso estratégico do Redis é o ponto chave deste projeto para garantir performance e eficiência no consumo da API externa.

### 1. Habilitação do Cache

* O cache é habilitado globalmente pela anotação `@EnableCaching` na classe principal `WeatherApiApplication.java`.

### 2. Configuração do Redis

A classe `RedisConfig.java` define a política de armazenamento de cache:

* **TTL (Time-To-Live):** O cache de cada entrada expira após **12 horas** (`.entryTtl(Duration.ofHours(12))`).
* **Não Armazenar Nulos:** `.disableCachingNullValues()` impede que requisições que resultam em `null` sejam cacheadas.
* **Serialização:** O uso de `GenericJackson2JsonRedisSerializer` assegura que o objeto Java (`Weather`) seja convertido corretamente para JSON no Redis e vice-versa, enquanto a classe `Weather` implementa `Serializable`.

### 3. Implementação do `@Cacheable`

A anotação `@Cacheable` é aplicada ao método de consulta no `WeatherController.java`:

```java
@Cacheable(value = "weather_single", key = "#country")
public Weather weather(@PathVariable String country) throws JsonProcessingException {
    // ... corpo do método (requisição à API externa)
}
```
**Mecanismo:** Na primeira requisição para um dado país/cidade (#country), o corpo do método é executado (chamada à API externa). Nas requisições subsequentes (Cache Hit) com a mesma chave, o resultado é servido diretamente do Redis, ignorando a chamada externa e o serviço de clima.





