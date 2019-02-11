# Config Server
Spring cloud confi server

# Prerequisites
- Start service registry instance

../service-registry

- Install mongodb and setup user
docker pull mongo

docker run -it -d -p 27017:27017 --name config-server-mongo --network=microservices-network mongo

docker exec -it config-server-mongo bash

mongo
use config-server

db.createUser(
   {
     user: "microservice_admin",
     pwd: "microservice_admin",
    roles: [ { role: "readWrite", db: "config-server" } ]
   }
 );


# Docker command:
- To generate local image
docker build -t config-server .

- To run docker image
docker run -d -p 8081:8081 --name config-server -e MONGO_HOST=config-server-mongo -e EUREKA_HOST=http://service-registry:9000/eureka --network=microservices-network config-server

- To check logs
docker logs --tail 2500 ContainerName/ContainerID
docker logs -f config-server

- To stop container
docker stop 3f8d88470b1c

- To remove container
docker container ls -a
docker container rm cc3f2ff51cab cd20b396a061