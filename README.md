# provysdb
Datasource for accessing PROVYS database.

Release artefacts are available from MichalStehlikCz/Maven package repository.

Database is configured using following parameters:\
provysdb.user - account used to login to Provys database\
provysdb.pwd - password used to login to Provys database\
provysdb.url - address of Provys database (e.g. localhost:1521:PVYS); passed to Oracle Thin JDBC driver\
provysdb.minpoolsize - minimal connection pool size, default 1\
provysdb.maxpoolsize - maximal connection pool size, default 10

