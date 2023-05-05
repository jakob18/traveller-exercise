# TRAVELLERS API
- Missing - Spring Security: "API should be secured for ‘service to service’ communication"

# Notes
- Because I used a MySQL database it is not possible to use: ``UNIQUE KEY unique_active_document (TRAVELLER_ID, IS_ACTIVE),`` because I can't put a where clause just to the TRUE values, the solution is to use a TRIGGER, but I rather manage it on Java


# SQL
## Install MySQL from dockers
* run ``` sudo docker pull mysql/mysql-server:latest ```
* run ``` sudo docker run -p 13308:3306 --name=travellersDB -eMYSQL_ROOT_PASSWORD=LetMeIn2023 -d mysql:latest ```
* run ``` sudo docker update --restart unless-stopped travellersDB ```
*    database: `travellers`
*    app username: `bmj`
*    app password: `LetMeIn`
