# Используем легкий JDK образ
FROM eclipse-temurin:21-jdk-jammy

# Рабочая директория внутри контейнера
WORKDIR /app

# Копируем jar файл
COPY target/*.jar app.jar

# Порт приложения
EXPOSE 8080

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]