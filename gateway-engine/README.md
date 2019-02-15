# Config Server
Spring cloud confi server

# Prerequisites
- Start service registry instance

../service-registry
../config-server



# Docker command:
- To generate local image
docker build -t gateway-engine .

- To run docker image
docker run -d -p 8071:8071 --name gateway-engine -e MONGO_HOST=config-server-mongo -e EUREKA_HOST=http://service-registry:9000/eureka --network=microservices-network gateway-engine

- To check logs
docker logs --tail 2500 ContainerName/ContainerID
docker logs -f gateway-engine

- To stop container
docker stop 3f8d88470b1c

- To remove container
docker container ls -a
docker container rm cc3f2ff51cab cd20b396a061
