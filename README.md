Project Specification
========================
> Java 17
> Spring 3.2.4
> PostgreSQL Database
> Swagger API Contract
> Maven 

 Set Up Instruction 
 ====================
1. Test Machine must have Docker installed.
2. Run Maven clean install - all unit tests should pass. The PostgreSQL database volume is configured in compose.yaml.
3. There's a init.sql file in the root location, which creates the "orders" table in PostgreSQL database.
4. Go to terminal as run "docker-compose up --build", once the service is up, go to next step
5. Go to Browser and open "http://localhost:8080/api/swagger-ui/index.html" - should be able to see three APIs.

6. To run the Google API locally need to update in application.properties key "google.maps.api.key" value.
    I have tested with my own key, and have kept a dummy value there for now.


    
