FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1 AS fileio
EXPOSE 8081
WORKDIR /fileio
ADD . /fileio
CMD sbt run
ENV DOCKERENV="TRUE"