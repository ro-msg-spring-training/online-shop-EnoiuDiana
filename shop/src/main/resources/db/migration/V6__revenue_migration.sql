CREATE TABLE IF NOT EXISTS Revenue(
ID INT PRIMARY KEY AUTO_INCREMENT,
LOCATION_ID INT,
CONSTRAINT FK_LOCATION FOREIGN KEY (LOCATION_ID) REFERENCES LOCATION(ID),
DATE_D DATE,
SUM DOUBLE
);
