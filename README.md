# automate
Automate is the awesome parking helper.

# ES
docker run --name=es -d -p 9200:9200 -p 9300:9300 daddykotex/es-no-volume:latest

# Node 
docker run --name=node -d -p 3000:3000 --link es:eshost daddykotex/automate-node:1.0.0
