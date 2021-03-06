echo "Make directory /conf_data/gateway-engine inside config-server-mongo"
docker exec -it config-server-mongo mkdir -p /conf_data/gateway-engine

echo "Copy /conf_data/config-server-data.js to config-server-mongo and gateway-engine-db-create"
docker cp ./conf_data/config-server-data.js config-server-mongo:/conf_data/gateway-engine/config-server-data.js
docker cp ./conf_data/gateway-engine-db-create.js config-server-mongo:/conf_data/gateway-engine/gateway-engine-db-create.js

echo "Copy /conf_data/load_gateway_conf.sh config-server-mongo"
docker cp ./conf_data/load_gateway_conf.sh config-server-mongo:/conf_data/gateway-engine/load_gateway_conf.sh

echo "Execute /conf_data/load_gateway_conf.sh"
docker exec -it config-server-mongo bash /conf_data/gateway-engine/load_gateway_conf.sh