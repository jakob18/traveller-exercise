# TRAVELLERS API




# SQL
## Install MySQL from dockers
* run ``` sudo docker pull mysql/mysql-server:latest ```
* run ``` sudo docker run -p 13308:3306 --name=travellersDB -eMYSQL_ROOT_PASSWORD=LetMeIn2023 -d mysql:latest ```
* run ``` sudo docker update --restart unless-stopped travellersDB ```
*    database: `travellers`
*    app username: `bmj`
*    app password: `LetMeIn`

## Docker to DB
