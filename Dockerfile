FROM openjdk:8

WORKDIR /app

ADD target/diplom-1.0.0-SNAPSHOT.jar .

CMD ["java", \
"-Duser.timezone=GMT+3:00", \
"-jar", "diplom-1.0.0-SNAPSHOT.jar"]