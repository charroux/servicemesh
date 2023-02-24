FROM openjdk:17
COPY build/libs/carservice-0.0.1-SNAPSHOT.jar .
CMD java -Xmx300m -Xms300m -XX:TieredStopAtLevel=1 -noverify -jar carservice-0.0.1-SNAPSHOT.jar
EXPOSE 8080
