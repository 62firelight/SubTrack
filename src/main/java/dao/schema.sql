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
    SubscriptionID varchar(50),
    subscriptionName varchar(50) not null,
    dateIssued timestamp,
    dateDue timestamp,
    subscriptionType varchar(50) not null,
    constraint SubscriptionID primary key (SubscriptionID)
);