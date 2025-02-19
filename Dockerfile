FROM openjdk:17

# Definindo o diretório de trabalho
WORKDIR /app

# Copiando o arquivo JAR gerado pelo Maven para o container
COPY target/Agronomia-0.0.1-SNAPSHOT.jar app.jar

# Expondo a porta onde a aplicação irá rodar
EXPOSE 8081

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
