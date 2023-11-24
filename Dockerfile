FROM openjdk:17
EXPOSE 8080
ADD target/timesheetapp.jar timesheetapp.jar
ENTRYPOINT ["java","-jar","timesheetapp.jar"]