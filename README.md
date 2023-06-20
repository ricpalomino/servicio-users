# servicio-users
Los pasos para levantar el proyecto es:

1.- Start el servicio-eureka-server (registro de servicios , balanceo de carga) http://localhost:8761/

2.- Start el servicio-users.

3.- Start el servicio-gateway-server (Punto central para el ruteo hacia nuestro servicio users) http://localhost:8090/api/v1

