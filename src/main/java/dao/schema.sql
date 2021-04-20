/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  yeah2
 * Created: 15 Apr 2021
 */

create table Customer(
    CustomerID int auto_increment,
    username varchar(50) not null,
    password varchar(50) not null,
    firstName varchar(50) not null,
    lastName varchar(50) not null,
    emailAddress varchar(50) not null,
    constraint CustomerID primary key (CustomerID)
);

create table Subscription(
    SubscriptionID int auto_increment,
    subscriptionName varchar(50) not null,
    subscriptionPrice decimal(10,2) not null,
    subscriptionType varchar(50) not null,
    category varchar(50) not null,
    companyName varchar(100) not null,
    description varchar(100),
    dateIssued date,
    dateDue date,
    paid boolean not null,
    constraint SubscriptionID primary key (SubscriptionID),
    constraint CustomerID_fk foreign key (CustomerID)
);