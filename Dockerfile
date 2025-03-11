# Используем официальный образ для Java
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл с зависимостями и билдим проект
COPY build/libs/*.jar app.jar

# Открываем порт приложения
EXPOSE 9090

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
