if (!gateway_mongo_user) {
    throw new Error("MongoDB gateway-engine user isn't set");
}
if (!gateway_mongo_password) {
    throw new Error("MongoDB gateway-engine user password isn't set");
}
if (!gateway_server_mongo) {
    throw new Error("Gateway engine database isn't set");
}

db = db.getSiblingDB(gateway_server_mongo);
db.createUser(
   {
     user: gateway_mongo_user,
     pwd: gateway_mongo_password,
    roles: [ { role: "readWrite", db: gateway_server_mongo } ]
   }
 );