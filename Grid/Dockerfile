FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1 AS grid
EXPOSE 8080
WORKDIR /grid
ADD . /grid
CMD sbt run
ENV DOCKERENV="TRUE"