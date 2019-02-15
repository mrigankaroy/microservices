mongo --eval "var config_mongo_user='microservice_admin'; var config_mongo_password='microservice_admin'; var config_server_mongo='config-server';" /conf_data/gateway-engine/config-server-data.js


mongo --eval "var gateway_mongo_user='gateway-admin'; var gateway_mongo_password='gateway-admin'; var gateway_server_mongo='gateway-engine';" /conf_data/gateway-engine/gateway-engine-db-create.js