FROM maven:3.6.3-jdk-11

COPY . /usr/src/app/

WORKDIR /usr/src/app

RUN mvn clean package

EXPOSE 8080

CMD ["mvn", "tomcat7:run"]