FROM node:5.0.0-wheezy

ADD package.json /app/package.json
RUN cd /app; npm install

ADD . /app/

EXPOSE 3000

ENTRYPOINT ["node", "/app/bin/www"] 

