### Docker ###
docker run -d --name oracle-db -p 1521:1521 -e ORACLE_PASSWORD=secret -e APP_USER=transporte_user -e APP_PASSWORD=transporte_pass -e APP_USER_PASSWORD=transporte_pass gvenzl/oracle-xe


### ENV ###
AZURE_TENANT_NAME
AZURE_POLICY_NAME
AZURE_CLIENT_ID

ORACLE_DB_URL
ORACLE_DB_USER
ORACLE_DB_PASSWORD