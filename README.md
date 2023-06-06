# JavaFX-Login-Logout
This is a simple JavaFX application that demonstrates how to implement a login/logout functionality using JavaFX

## Getting Started

Use `git clone` to clone this repository:

```console
git clone https://github.com/RedDotz20/JavaFX-Login-Logout.git
```

or click `Clone or download` and `Download ZIP` to get this repo.

## Dependencies

This project has the following dependencies:
- JavaFX: The JavaFX libraries are required to build and run the JavaFX application.
- [MySQL connector J 8.0.33](https://dev.mysql.com/downloads/connector/j/)
- [JDK 1.8 (default)](https://www.oracle.com/ph/java/technologies/javase/javase8-archive-downloads.html)
- [font-awesome-fx 8.2](https://jar-download.com/artifacts/de.jensd/fontawesomefx/8.2/source-code)

## Create / Select MySQL Database

```sql
CREATE DATABASE databasename;
USE databasename;
```

## Create users Table

```sql
CREATE TABLE users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL
);
```

## Create `.env` File

```console
touch .env
```

#### .env file config

```env
# Example Connection Config
DB_HOST=localhost
DB_USER=admin
DB_PASSWORD=admin
```
