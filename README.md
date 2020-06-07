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
Page Object, Page Element, PageFactory and Facade pattern

UI Map - properties + parser //TODO

Logger - Slf4j

Reporter - Allure

Lombok

assembly plugin - make executable jar with dependency

run with:

mvn clean compile assembly:single -DskipTests

java -jar target/AT_UI_Demo-SNAPSHOT-1.0.jar

