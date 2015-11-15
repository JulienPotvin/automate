FROM node:5.0.0-wheezy

ADD . /app
RUN cd /app; npm install

EXPOSE 3000

ENTRYPOINT ["node", "/app/bin/www"] 

