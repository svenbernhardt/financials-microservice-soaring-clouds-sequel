FROM java:8
VOLUME /tmp
COPY financials-ms/target/financials-microservice*.jar financials.jar
EXPOSE 7777
RUN bash -c 'touch /financials.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/financials.jar"]
