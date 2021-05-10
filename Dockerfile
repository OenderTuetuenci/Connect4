FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1 AS game
EXPOSE 8082
RUN \
  apt-get update && \
  apt-get install -y sbt libxrender1 libxtst6 libxi6 && \
  apt install -y xauth && \
  xauth add onder-ThinkPad-E495/unix:0  MIT-MAGIC-COOKIE-1  19faa2cfd150958d760def6d6fe6892e
WORKDIR /game
ADD . /game
CMD sbt run
ENV DOCKERENV="TRUE"