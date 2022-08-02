CREATE TABLE IF NOT EXISTS Supplier(
                                       ID INT PRIMARY KEY AUTO_INCREMENT,
                                       NAME VARCHAR(255));

CREATE TABLE IF NOT EXISTS ProductCategory(
                                              ID INT PRIMARY KEY AUTO_INCREMENT,
                                              NAME VARCHAR(255),
                                              DESCRIPTION VARCHAR(255));

CREATE TABLE IF NOT EXISTS Product(
                                      ID INT PRIMARY KEY AUTO_INCREMENT,
                                      NAME VARCHAR(255),
                                      DESCRIPTION VARCHAR(255),
                                      PRICE DOUBLE,
                                      WEIGHT DOUBLE,
                                      IMAGE_URL VARCHAR(255),
                                      PRODUCT_CATEGORY_ID INT,
                                      CONSTRAINT FK_PRODUCT_CATEGORY FOREIGN KEY (PRODUCT_CATEGORY_ID) REFERENCES PRODUCTCATEGORY(ID),
                                      SUPPLIER_ID INT,
                                      CONSTRAINT FK_SUPPLIER FOREIGN KEY (SUPPLIER_ID) REFERENCES SUPPLIER(ID));

CREATE TABLE IF NOT EXISTS Customer(
                                       ID INT PRIMARY KEY AUTO_INCREMENT,
                                       FIRSTNAME VARCHAR(255),
                                       LASTNAME VARCHAR(255),
                                       USERNAME VARCHAR(255),
                                       PASSWORD VARCHAR(255),
                                       EMAIL_ADDRESS VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Location(
                                       ID INT PRIMARY KEY AUTO_INCREMENT,
                                       NAME VARCHAR(255),
                                       ADDRESS_COUNTRY VARCHAR(255),
                                       ADDRESS_CITY VARCHAR(255),
                                       ADDRESS_COUNTY VARCHAR(255),
                                       ADDRESS_STREET VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Revenue(
                                      ID INT PRIMARY KEY AUTO_INCREMENT,
                                      LOCATION_ID INT,
                                      CONSTRAINT FK_LOCATION FOREIGN KEY (LOCATION_ID) REFERENCES LOCATION(ID),
                                      DATE_D DATE,
                                      SUM DOUBLE
);
CREATE TABLE IF NOT EXISTS PlacedOrder(
                                          ID INT PRIMARY KEY AUTO_INCREMENT,
                                          SHIPPED_FROM_ID INT,
                                          CONSTRAINT FK_SHIPPED_FROM FOREIGN KEY (SHIPPED_FROM_ID) REFERENCES LOCATION(ID),
                                          CUSTOMER_ID INT,
                                          CONSTRAINT FK_CUSTOMER FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(ID),
                                          CREATED_AT DATETIME,
                                          ADDRESS_COUNTRY VARCHAR(255),
                                          ADDRESS_CITY VARCHAR(255),
                                          ADDRESS_COUNTY VARCHAR(255),
                                          ADDRESS_STREET VARCHAR(255));


CREATE TABLE IF NOT EXISTS Stock(
                                    ID INT PRIMARY KEY AUTO_INCREMENT,
                                    PRODUCT_ID INT,
                                    CONSTRAINT FK_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(ID),
                                    LOCATION_ID INT,
                                    CONSTRAINT FK_LOCATION_STOCK FOREIGN KEY (LOCATION_ID) REFERENCES LOCATION(ID),
                                    QUANTITY INT,
                                    CONSTRAINT UNIQUE_STOCK UNIQUE (PRODUCT_ID,LOCATION_ID)
);

CREATE TABLE IF NOT EXISTS OrderDetail(
                                          ID INT PRIMARY KEY AUTO_INCREMENT,
                                          PRODUCT_ID INT,
                                          CONSTRAINT FK_PRODUCT_ORDER_DETAIL FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(ID),
                                          ORDER_ID INT,
                                          CONSTRAINT FK_ORDER FOREIGN KEY (ORDER_ID) REFERENCES PLACEDORDER(ID),
                                          QUANTITY INT,
                                          CONSTRAINT UNIQUE_ORDER_DETAIL UNIQUE (PRODUCT_ID,ORDER_ID)
);
