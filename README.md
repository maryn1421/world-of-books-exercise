# world-of-books-exercise

#### Technologies:

- Java version: 8 <br>
- Spring boot framework
  
- Database: PostgreSQL , ORM : Hibernate JPA

#### How to start:

Before executing you have to define some environment variables:

##### database:
- database url
- database username
- database password

##### ftp:
- ftp url
- ftp port
- ftp username
- ftp password


The properties file is in the ```src/main/resources``` folder.

After declaring these variables the program will fetch the data from the given
API, the report and importLog files are generated automatically.


#### Report and importLog
The reports are generated into ``src/main/resources/files`` folder, the number in its name is time when it was created. <br>
The importLogs are in the ```src/main/resources/logs``` directory.


If you have any question, feel free to contact me: zolnaimaryn1421@gmail.com