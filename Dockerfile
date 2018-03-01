FROM java:8
VOLUME /tmp
ADD ./target/financials-microservice-1.0.0.0.jar financials.jar
EXPOSE 7777
RUN bash -c 'touch /financials.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/financials.jar"]
