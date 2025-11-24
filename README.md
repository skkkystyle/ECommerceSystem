Этот проект реализует микросервисную систему интернет-магазина с использованием:
- **Java 17**
- **Spring Boot**
- **Maven**
- **Docker / Docker Compose**
- **RabbitMQ (Message Queue)**
- **PostgreSQL**
- **Clean Architecture + DDD**
- **Transactional Outbox / Inbox Pattern**
- **Swagger UI** — для документации REST API
- **WebSocket** — уведомления о статусе заказа

---

## Структура проекта

```
ecommerce/
├── api-gateway/              # API Gateway (Spring Cloud Gateway)
├── orders-service/            # Управление заказами
├── payments-service/          # Управление счетами
├── docker-compose.yml         # Оркестрация контейнеров
└── README.md                  # Этот файл
```

---

## Установка и запуск

### Предварительные требования

- Docker
- Maven
- Java 17 SDK

---

### 1. Собрать каждый из модулей (api-gateway, orders-service, payments-service)

```bash
mvn clean package
```

---

### 2. Запуск через Docker Compose

```bash
docker-compose up --build
```

---

### 3. Проверка работы

- **API Gateway**: http://localhost:8080
- **Orders Service**: http://localhost:8081
- **Payments Service**: http://localhost:8082
- **RabbitMQ Management UI**: http://localhost:15672 (логин/пароль: `guest` / `guest`)
- **Swagger UI**:
    - http://localhost:8081/swagger-ui/index.html
    - http://localhost:8082/swagger-ui/index.html

---

## Возможные проблемы и решения

### Очередь `order.created` не существует
**Решение**:
- Вручную создать очередь через UI RabbitMQ (Type: Classic, name: "order.created")
