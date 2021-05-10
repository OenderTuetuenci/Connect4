FROM mongo:latest
EXPOSE 27017:27017

ENV MONGO_INITDB_DATABASE=connect4

ENV TZ=Europe/Amsterdam

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
	echo $TZ > /etc/timezone