FROM mysql:latest
EXPOSE 3306:3306

ENV MYSQL_ROOT_PASSWORD=bestpasswordever
ENV MYSQL_DATABASE=connect4
ENV MYSQL_USER=connect4
ENV MYSQL_PASSWORD=bestpasswordever

ENV TZ=Europe/Amsterdam

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
	echo $TZ > /etc/timezone