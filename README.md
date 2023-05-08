# TRAVELLERS API

# Notes
- Because I used a MySQL database it is not possible to use: ``UNIQUE KEY unique_active_document (TRAVELLER_ID, IS_ACTIVE),`` only for ``Is_Active=true`` that means that I can't put a where clause just to the TRUE values, the solution is to use a TRIGGER, but I rather manage it on Java
- Doubt: I have an email or mobile number in one traveller, but the traveller is inactive, should I let another traveller use that email or mobile? I will not allow it until the Product Owner decide it
- If I had time, I would develop in TDD, or at least write the tests has I was developing, but like every project, we are always in the red when it comes to time to delivery...
- In Spring Security I created with a Basic Authentication because it is a service to service communication, if it was a normal frontend to backend I would had used JWT
- The username and password is on application.yml for DEV but for PROD it should be on system environment properties
- Implement Reactive Programming with WebFlux
- I don't like the approach of one get to the three types of data, so I created the three methods for the different gets

# SQL
## Install MySQL from dockers
* run ``` sudo docker pull mysql/mysql-server:latest ```
* run ``` sudo docker run -p 13308:3306 --name=travellersDB -eMYSQL_ROOT_PASSWORD=LetMeIn2023 -d mysql:latest ```
* run ``` sudo docker update --restart unless-stopped travellersDB ```
*    database: `travellers`
*    app username: `bmj`
*    app password: `LetMeIn`
