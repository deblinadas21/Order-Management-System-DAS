Project Specification
========================
> Java 17
> Spring 3.2.4
> PostgreSQL Database
> Swagger
> Maven 

 Set Up Instruction 
 ====================
1. Test Machine must have Docker installed.
2. Update Google API key, in application.properties key "google.maps.api.key" value. I have tested with my own key, and have kept a dummy value there for now.   
3. Run "mvn clean install" - all unit and integration tests should pass. 
4. There's no separate startup.sh, as the postgreSQL database volume will be up as part of compose (will execute init.sql automatically and set up "orders" database) 
5. Go to terminal as run "docker-compose up --build", once the service is up verify below
6. Run "docker ps" and verify both applciation and database are up and running.
   
CONTAINER ID   IMAGE             COMMAND                  CREATED          STATUS         PORTS                    NAMES
b6551731ce87   demo-app:latest   "java -jar /demo-0.0…"   8 minutes ago    Up 8 minutes   0.0.0.0:8080->8080/tcp   demo-app
ce3fbc1f0837   postgres:latest   "docker-entrypoint.s…"   24 minutes ago   Up 8 minutes   0.0.0.0:5432->5432/tcp   demo-postgres-1

8. Go to Browser and open "http://localhost:8080/swagger-ui/index.html" - should be able to see three APIs.


    
