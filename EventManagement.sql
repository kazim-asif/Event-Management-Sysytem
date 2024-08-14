CREATE DATABASE universityDB;
use universityDB;

CREATE TABLE Students (
  id INT PRIMARY KEY,
  name VARCHAR(50),
  programId VARCHAR(30)
);

CREATE TABLE Staff (
  id INT PRIMARY KEY,
  name VARCHAR(50)
);

CREATE TABLE StudyPrograms (
  id INT PRIMARY KEY,
  name VARCHAR(50),
  programResponsibleId INT,
  FOREIGN KEY (programResponsibleId) REFERENCES Staff(id)
);

CREATE TABLE Guests (
  id INT PRIMARY KEY,
  eventId INT,
  name VARCHAR(50),
  invitedByStudentId INT
);

CREATE DATABASE eventDB;
use eventDB;

CREATE TABLE RegisterEvents (
  id INT PRIMARY KEY,
  studentId INT,
  FOREIGN KEY (studentId) REFERENCES universityDB.Students(id)
);







