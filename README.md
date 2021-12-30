<h2 align="center">SpringBoot - KeyCloak</h2>

<p align="center">
  <img alt="Java Logo" src="https://img.shields.io/badge/Java-11-00143c?logo=java&labelColor=fff&logoColor=red">&nbsp;&nbsp;
  <img alt="Java Logo" src="https://img.shields.io/badge/Spring%20Boot-2.6.2-00143c?logo=spring&labelColor=fff&logoColor=green">&nbsp;&nbsp;
  <img alt="Docker Logo" src="https://img.shields.io/badge/Docker & Docker Compose-🛠-00143c?logo=docker&labelColor=fff">&nbsp;&nbsp;
</p>

<p align="center">API em SpringBoot que visa validar a autenticação realizada pelo KeyCloak</p>

<br>

## KeyCloak Install

Esse comando irá baixar a imagem e iniciar um container do KeyCloak em seu docker.
```bash
docker run -p 9080:8080 --name=keycloak quay.io/keycloak/keycloak:16.1.0
```  

Esse outro comando é para criar um usuário admin para acesso ao painel (user: admin, pass: admin).
```
docker exec keycloak /opt/jboss/keycloak/bin/add-user-keycloak.sh -u admin -p admin
```  

Você pode acessar o painel em <a href="http://localhost:9080/auth/admin">http//localhost:9080/auth/admin</a>

Agora será necessário a criação de um Realm (MyRealm), um Client, um Client Scope, alguns usuários para testes e pelo menos duas Roles (ADMIN e USER).  
Crie também um Mapper em seu Client, com o nome de 'athorities', do tipo 'User Realm Role'.  

Qualquer dúvida aconselho acessar o <a href="https://www.keycloak.org/getting-started/getting-started-docker">Getting Starter</a>  
