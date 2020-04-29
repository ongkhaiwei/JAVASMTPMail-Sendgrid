
FROM java:8
WORKDIR /
ADD okw.mail-1.0-SNAPSHOT-jar-with-dependencies.jar okw.mail-1.0-SNAPSHOT-jar-with-dependencies.jar
CMD java -jar okw.mail-1.0-SNAPSHOT-jar-with-dependencies.jar