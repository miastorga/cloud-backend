### Docker ###
docker run -d --name oracle-db -p 1521:1521 -e ORACLE_PASSWORD=secret -e APP_USER=transporte_user -e APP_PASSWORD=transporte_pass -e APP_USER_PASSWORD=transporte_pass gvenzl/oracle-xe

### Docker PROD ###
sudo docker run -d -p 1521:1521 -e ORACLE_PASSWORD=transporte_pass -e APP_USER=transporte_user -e APP_USER_PASSWORD=transporte_pass --name oracle-db --restart always gvenzl/oracle-xe


### ENV VARIABLES ###
```text
AZURE_TENANT_NAME
AZURE_POLICY_NAME
AZURE_CLIENT_ID

SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```