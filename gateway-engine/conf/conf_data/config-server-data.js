if (!config_mongo_user) {
    throw new Error("MongoDB config-server user isn't set");
}
if (!config_mongo_password) {
    throw new Error("MongoDB config-server user password isn't set");
}
if (!config_server_mongo) {
    throw new Error("Config server database isn't set");
}

db = db.getSiblingDB(config_server_mongo);
db.auth(config_mongo_user, config_mongo_password);

db.configProfile.insert({
    "_class": "com.mriganka.microservices.config_server.entity.ConfigProfile",
    "application": "gateway-engine",
    "profile": "default",
    "version": 1,
    "properties":
        [
            {"key":"gateway.protocol", "value": "http://"},
            {"key":"gateway.connection.timeout", "value": "600000"},
            {"key":"gateway.socket.timeout", "value": "600000"},
            {"key":"mongo.host", "value": "config-server-mongo"},
            {"key":"mongo.port", "value": "27017"},
            {"key":"mongo.user", "value": "gateway-admin"},
            {"key":"mongo.password", "value": "gateway-admin"},
            {"key":"mongo.dbName", "value": "gateway-engine"}
        ]
});