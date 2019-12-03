# provysdb
Datasource for accessing PROVYS database.

Release artefacts are available from MichalStehlikCz/Maven package repository.

Database is configured using following parameters:\
PROVYSDB_USER - account used to login to Provys database\
PROVYSDB_PWD - password used to login to Provys database\
PROVYSDB_URL - address of Provys database (e.g. localhost:1521:PVYS); passed to Oracle Thin JDBC driver\
PROVYSDB_MINPOOLSIZE - minimal connection pool size, default 1\
PROVYSDB_MAXPOOLSIZE - maximal connection pool size, default 10

