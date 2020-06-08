Тестовое задание по автоматизации UI
===============
Стек:
---------------
- Java 8
- Maven 3.6.2 (Wrapper)
- JUnit 5
- Selenide
- Serenity BDD

Архитектура проекта и доп инструменты:
---------------
UI Map - properties + parser

Logger - Slf4j

Reporter - Allure

Lombok

Run tests Ветка test_selenide:

mvn clean test allure:report

./mvnw clean test allure:report

