# Service Registry
Spring cloud netflix service registry

# Docker command:
- To generate local image
docker build -t service-registry .

- To create network
docker network create microservices-network

- To run docker image
docker run -d -p 9000:9000 --name service-registry --network=microservices-network service-registry

- To check logs
docker logs --tail 2500 ContainerName/ContainerID
docker logs -f service-registry

- To stop container
docker stop 3f8d88470b1c

- To remove container
docker container ls -a
docker container rm cc3f2ff51cab cd20b396a061

- To remove image
docker rmi 9ed58f370b64