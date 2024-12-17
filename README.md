# REST API Проект: Управление Проектами

## Описание
Данный проект представляет собой REST API для управления проектами. Проект позволяет 
добавлять, 
обновлять, 
удалять и 
получать информацию о проектах, а также управлять разделами внутри проекта. 
Реализована аутентификация и авторизация с использованием JWT токенов.

## Технологии
- **Java** 17+
- **Spring Boot** 3.x
- **PostgreSQL**
- **Liquibase**
- **Docker / Docker Compose**
- **JWT** для аутентификации
- **Lombok**

## Функционал
### 1. Управление проектами
- **Добавление проекта**  
- **Удаление проекта**  
- **Обновление проекта**  
- **Получение информации о проекте**  

### 2. Управление разделами проекта
Проект включает в себя список разделов, которые можно добавлять и удалять:
- АР
- КР
- ИОС1
- ИОС2
- ИОС3
- ИОС4
- ИОС5
- ИОС6

### 3. Аутентификация
- Регистрация пользователя
- Логин пользователя
- Обновление токена (refresh)
- Выход из системы

## Endpoints

### Аутентификация

| Метод  | Endpoint                | Описание                           |
|--------|-------------------------|------------------------------------|
| POST   | `/api/v1/auth/register` | Регистрация пользователя           |
| POST   | `/api/v1/auth/login`    | Аутентификация и получение токена  |
| POST   | `/api/v1/auth/refresh`  | Обновление JWT токена              |
| POST   | `/api/v1/auth/logout`   | Выход из системы                   |

### Управление проектами

| Метод  | Endpoint                     | Описание                      |
|--------|------------------------------|-------------------------------|
| GET    | `/api/projects`              | Получить список всех проектов |
| GET    | `/api/projects/{id}`         | Получить проект по ID         |
| POST   | `/api/projects`              | Добавить новый проект         |
| PUT    | `/api/projects/{id}`         | Обновить проект по ID         |
| DELETE | `/api/projects/{id}`         | Удалить проект по ID          |

### Управление разделами

| Метод  | Endpoint                                   | Описание                             |
|--------|--------------------------------------------|--------------------------------------|
| POST   | `/api/projects/{projectId}/sections`       | Добавить раздел в проект             |
| GET    | `/api/projects/{projectId}/sections`       | Получить список разделов проекта     |
| DELETE | `/api/projects/{projectId}/sections/{id}`  | Удалить раздел из проекта по ID      |

## Сборка и запуск

### 1. **Склонируйте репозиторий**

   ```bash
   git clone https://github.com/IronTommy/ProjectManagementApi
   cd ProjectManagementApi
   ```

### 2. **Сборка проекта с использованием Maven**

   ```bash
   mvn clean install
   ```

### 3. **Запуск через Docker Compose**

   Проект включает `Dockerfile` и `docker-compose.yml` для автоматического развертывания API и базы данных.

   ```bash
   docker-compose up --build
   ```
### 4. **Liquibase**

   Миграции для базы данных будут применены автоматически при запуске контейнера.

#### После успешного запуска приложение будет доступно по адресу `http://localhost:8080`.

### 5: Проверка работы с использованием CURL

#### Регистрация пользователя:

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
-H "Content-Type: application/json" \
-d '{
  "firstName": "Иван",
  "lastName": "Иванов",
  "email": "ivan@example.com",
  "password1": "password123",
  "password2": "password123",
  "captchaCode": "1234",
  "captchaSecret": "b8a5e53c-d6f3-4bd5-b7ab-cbf52d92a202"
}'
```

#### Логин пользователя:

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
-H "Content-Type: application/json" \
-d '{
  "email": "ivan@example.com",
  "password": "password123"
}'
```
#### Получение списка проектов:

```bash
curl -X GET http://localhost:8080/api/projects \
-H "Authorization: Bearer <ACCESS_TOKEN>"
```

#### Добавление нового проекта:

```bash
curl -X POST http://localhost:8080/api/projects \
-H "Authorization: Bearer <ACCESS_TOKEN>" \
-H "Content-Type: application/json" \
-d '{
  "name": "Новый проект",
  "code": "PRJ001",
  "startDate": "2024-06-01",
  "endDate": "2024-12-01",
  "status": "ACTIVE"
}'
```

## Структура проекта

```plaintext
src/
├── main/
│   ├── java/com/example/projects/
│   │   ├── controller/   # Контроллеры API
│   │   ├── dto/          # DTO классы
│   │   ├── entity/       # JPA сущности
│   │   ├── repository/   # Репозитории
│   │   ├── service/      # Сервисы
│   │   └── config/       # Конфигурации Spring и Security
│   ├── resources/
│   │   ├── db/
│   │   │   └── changelog/ # Liquibase changelog
│   │   ├── application.yml
│   │   └── static/
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

## Авторизация
- Аутентификация основана на JWT токенах.
- Доступ к защищённым ресурсам осуществляется с использованием заголовка `Authorization: Bearer {token}`.

## Пример конфигурации Docker Compose
```yaml
version: '3.8'
services:
  db:
    image: postgres:15
    container_name: project_db
    environment:
      POSTGRES_DB: project_db
      POSTGRES_USER: project_username
      POSTGRES_PASSWORD: project_password
    ports:
      - "5432:5432"
  api:
    build: .
    container_name: project_api
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://localhost:5432/project_db
      SPRING_DATASOURCE_USERNAME: project_user
      SPRING_DATASOURCE_PASSWORD: project_password
```

## Контакты
Для вопросов и предложений:  
Email: `timullka@gmail.com`  
GitHub: [https://github.com/IronTommy](https://github.com/IronTommy)
