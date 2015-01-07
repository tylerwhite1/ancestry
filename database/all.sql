create database ancestryDB;
grant all privileges on ancestryDB.* to ancestryDBUser@localhost identified by 'ancestryDBPwd';

/* (2) create the table */
use ancestryDB;
create table user
(
   userId integer not null,
   password varchar(32) not null,
   firstName varchar(32) not null,
   lastName varchar(32) not null,
   email varchar(64) not null, 
   gender varchar(8) not null,
   birthDate varchar (30) not null,
   activation varchar(8) not null,
   fatherId integer not null,
   motherId integer not null
);

create table invitation
(
   userId integer not null,
   groupId integer not null,
   accept varchar(32) not null,
   inviteId integer not null
);

create table familygroup
(
   groupId integer not null,
   groupName varchar(32) not null,
   createById integer not null
);

create table groupmembers
(
   userId integer not null,
   familyGroupId integer not null
);

create table parentnotification
(
   notificationId integer not null,
   userId integer not null,
   parentFirstName varchar(25) not null,
   parentLastName varchar(25) not null
);

insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (206,'123', 'Sam', 'Washington', 'rolltidefan@yahoo.com', 'Male', '1991-05-11','unlock', 639,0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (222,'abc', 'Tom', 'Middleton', 'avengerofclan@yahoo.com', 'Male','1989-12-12', 'lock', 255,0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (281,'jwash', 'Jamel', 'Washington', 'jamelwash@gmail.com', 'Male','1972-11-02', 'unlock', 0,0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (210,'210', 'Jasmine', 'Washington', 'jwalker92@gmail.com', 'Female','1974-10-12', 'lock', 0,0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (255,'love', 'Cory', 'Middleton', 'heat4life@yahoo.com', 'Male','1975-05-11', 'unlock', 0,0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (639,'tyler', 'Tyler', 'White', 'twhite12@my.westga.edu', 'Male','1991-07-16', 'unlock', 0,0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (145,'123', 'Mellia', 'Walberg', 'fantasy12@yahoo.com', 'Female','11/02/2009', 'unlock', 0,0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (650,'super', 'Allen', 'Kentt', 'superman@yahoo.com', 'Male','11/11/2013', 'unlock', 852, 534);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (736,'apple', 'Alex', 'Washington', 'alexw@yahoo.com', 'Male','03/09/2014', 'unlock', 206, 210);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (852,'123', 'Clark', 'Kent', 'ck@yahoo.com', 'Male','05/28/2012', 'unlock', 0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (534,'000', 'Lois', 'Lane', 'll@gmail.com', 'Female','05/05/2014', 'unlock', 721, 320);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (721,'000', 'Joshua', 'Lane', 'laneman@yahoo.com', 'Male','12/01/2014', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (320,'abc', 'Martha', 'Lane', 'lanem@gmail.com', 'Female','06/16/2014', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (245,'111', 'Kelly', 'Lane', 'kl@yahoo.com', 'Female','07/30/2013', 'unlock',721, 320);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (301,'789', 'Carl', 'Jones', 'cjones@yahoo.com', 'Male','02/16/2010', 'unlock',255, 245);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (289,'123', 'Ashley', 'Lane', 'babySuper@yahoo.com', 'Female','12/31/2013', 'unlock',852, 534);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (699,'abc', 'Chris', 'Cole', 'coleworld@yahoo.com', 'Male', '07/08/2013', 'unlock',0, 289);


insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1000,'123', 'David', 'Nole', 'dn@yahoo.com', 'Male','02/31/200', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1001,'abc', 'Chrissy', 'Ball', 'cball@yahoo.com', 'Female', '07/08/1997', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1002,'abc', 'Molly', 'Nole', 'mnole@yahoo.com', 'Female', '07/08/1977', 'unlock',1000, 1001);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1003,'abc', 'Carlos', 'Nole', 'cnole@yahoo.com', 'Male', '07/08/1979', 'unlock',1000, 1001);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1004,'abc', 'Sandy', 'Nole', 'sand@yahoo.com', 'Female', '01/08/1977', 'unlock',1000, 1001);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1014,'abc', 'Kevin', 'Brown', 'kb@yahoo.com', 'Male', '01/08/1972', 'unlock',1003, 1006);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1005,'abc', 'Carl', 'Nalem', 'nalem@yahoo.com', 'Male', '07/012/1977', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1006,'abc', 'Liza', 'Brown', 'lizeb@yahoo.com', 'Female', '07/08/1983', 'unlock',1008, 1009);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1007,'abc', 'Jhena', 'Brown', 'jhenoaB@yahoo.com', 'Female', '11/08/1977', 'unlock',1008, 1009);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1008,'abc', 'Julius', 'Washington', 'jwash58@yahoo.com', 'Male', '12/08/1977', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1009,'abc', 'Carly', 'Washington', 'carlwash@yahoo.com', 'Female', '11/11/1976', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1011,'abc', 'Gail', 'Watkins', 'gwatkins58@yahoo.com', 'Female', '10/08/1955', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1012,'abc', 'Jessica', 'Nole', 'jnole@yahoo.com', 'Female', '12/08/1967', 'unlock',1005, 1002);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1013,'abc', 'Fred', 'Nole', 'fNole58@yahoo.com', 'Male', '12/08/1950', 'unlock',1005, 1002);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1015,'abc', 'Jessica', 'Sanders', 'jsanders8@yahoo.com', 'Female', '02/08/1950', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1016,'abc', 'Trey', 'Nole', 'treyne@yahoo.com', 'Male', '10/08/1945', 'unlock',0, 1011);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1017,'abc', 'Carly', 'Jones', 'cyjs@yahoo.com', 'Female', '11/08/1967', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1018,'abc', 'Paul', 'Nole', 'plne8@yahoo.com', 'Male', '12/08/1980', 'unlock',1016, 1017);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1019,'abc', 'Calvin', 'Nole', 'calvinNole@yahoo.com', 'Male', '02/08/1983', 'unlock',1013, 1015);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1020,'abc', 'Tonya', 'Nole', 'tonya@yahoo.com', 'Female', '10/08/1985', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1021,'abc', 'Tammy', 'Nole', 'tamNole@yahoo.com', 'Female', '11/21/1986', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1022,'abc', 'Kristi', 'Nole', 'knole@yahoo.com', 'Female', '02/08/1980', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1023,'abc', 'Tom', 'Smith', 'tsmith1@yahoo.com', 'Male', '04/18/1983', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1028,'abc', 'Cori', 'Smith', 'coris12@yahoo.com', 'Female', '10/15/1998', 'unlock',1023, 1022);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1025,'abc', 'Travis', 'Nole', 'trey@yahoo.com', 'Male', '02/21/1986', 'unlock',1019, 1020);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1026,'abc', 'Edward', 'Nole', 'ed@yahoo.com', 'Male', '02/08/1984', 'unlock',1019, 1020);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1027,'abc', 'Tara', 'Nole', 'taran@yahoo.com', 'Female', '04/18/1985', 'unlock',1019, 1020);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1029,'abc', 'Rachel', 'Smith', 'rsmith@yahoo.com', 'Female', '02/08/1990', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1031,'abc', 'Henry', 'Smith', 'henry@yahoo.com', 'Male', '04/18/1990', 'unlock',0, 1029);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1032,'abc', 'Jacob', 'Barnes', 'jbarnes@yahoo.com', 'Male', '05/11/1991', 'unlock',0, 1033);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1033,'abc', 'Ashley', 'Barnes', 'ab@yahoo.com', 'Female', '07/25/1985', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1035,'abc', 'Mary', 'Barnes', 'maryb@yahoo.com', 'Female', '04/18/1996', 'unlock',1032, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1036,'abc', 'Matthew', 'Barnes', 'matt@yahoo.com', 'Male', '12/02/1996', 'unlock',1032, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1037,'abc', 'Wes', 'Barnes', 'magBarnes@yahoo.com', 'Male', '01/25/1985', 'unlock',1032, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1038,'abc', 'Ricki', 'Smith', 'rickismith@yahoo.com', 'Female', '11/18/1996', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1039,'abc', 'Beth', 'Barnes', 'beth@yahoo.com', 'Female', '09/22/2001', 'unlock',1036, 1038);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1040,'abc', 'Kim', 'Wade', 'wade@yahoo.com', 'Female', '01/25/1999', 'unlock',0, 0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (1041,'abc', 'Sandra', 'White', 'whites@yahoo.com', 'Female', '04/18/2006', 'unlock',1037, 1040);








insert into invitation (userId, groupId, accept, inviteId) values (281, 1297, 'waiting', 892);
insert into invitation (userId, groupId, accept, inviteId) values (281, 1881, 'waiting', 743);

insert into groupmembers (userId, familyGroupId) values (255, 1881);
insert into groupmembers (userId, familyGroupId) values (210, 1881);
insert into groupmembers (userId, familyGroupId) values (255, 1297);
insert into groupmembers (userId, familyGroupId) values (145, 1297);
insert into groupmembers (userId, familyGroupId) values (210, 1297);

insert into familygroup (groupId, groupName, createById) values (2057, 'Middleton Side', 222);
insert into familygroup (groupId, groupName, createById) values (1881, 'white', 255);
insert into familygroup (groupId, groupName, createById) values (1297, 'Middleton', 255);


insert into parentnotification (notificationId, userId, parentFirstName, parentLastName) values (1555, 1036, 'Sarah', 'Nole');
insert into parentnotification (notificationId, userId, parentFirstName, parentLastName) values (1556, 1035, 'Sarah', 'Nole');
insert into parentnotification (notificationId, userId, parentFirstName, parentLastName) values (1557, 1037, 'Sarah', 'Nole');
insert into parentnotification (notificationId, userId, parentFirstName, parentLastName) values (1559, 1016, 'Chris', 'Nole');








insert into parentnotification (notificationId, userId, parentFirstName, parentLastName) values (1197, 721, 'John', 'Lane');
insert into parentnotification (notificationId, userId, parentFirstName, parentLastName) values (1636, 721, 'Rachel', 'Lane');
insert into parentnotification (notificationId, userId, parentFirstName, parentLastName) values (1299, 320, 'Tod', 'Belly');
insert into parentnotification (notificationId, userId, parentFirstName, parentLastName) values (1852, 320, 'Jasmine', 'Belly');
insert into parentnotification (notificationId, userId, parentFirstName, parentLastName) values (2994, 245, 'Tod', 'Belly');
insert into parentnotification (notificationId, userId, parentFirstName, parentLastName) values (1947, 245, 'Jasmine', 'Belly');
insert into parentnotification (notificationId, userId, parentFirstName, parentLastName) values (1670, 301, 'Tod', 'Belly');
insert into parentnotification (notificationId, userId, parentFirstName, parentLastName) values (2595, 301, 'Jasmine', 'Belly');













create database ancestryDB;
grant all privileges on ancestryDB.* to ancestryDBUser@localhost identified by 'ancestryDBPwd';

/* (2) create the table */
use ancestryDB;
create table user
(
   userId integer not null,
   password varchar(32) not null,
   firstName varchar(32) not null,
   lastName varchar(32) not null,
   email varchar(64) not null, 
   gender varchar(8) not null,
   birthDate varchar (30) not null,
   activation varchar(8) not null,
   fatherId integer not null,
   motherId integer not null
);

create table invitation
(
   userId integer not null,
   groupId integer not null,
   accept varchar(32) not null,
   inviteId integer not null
);


insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (206,'123', 'Sam', 'Washington', 'rolltidefan@yahoo.com', 'Male', '1991-05-11','lock', 281,210);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (222,'abc', 'Tom', 'Middleton', 'avengerofclan@yahoo.com', 'Male','1989-12-12', 'lock', 255,0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (281,'jwash', 'Jamel', 'Washington', 'jamelwash@gmail.com', 'Male','1972-11-02', 'lock', 0,0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (210,'210', 'Jasmine', 'Washington', 'jwalker92@gmail.com', 'Female','1974-10-12', 'lock', 0,0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (255,'love', 'Cory', 'Middleton', 'heat4life@yahoo.com', 'Male','1975-05-11', 'lock', 0,0);
insert into user (userId, password,firstName, lastName, email, gender,birthDate, activation,fatherId,motherId) values (271,'ok', 'Bob', 'Carter', 'bc12@yahoo.com', 'Male','05/14/1977', 'unlock', 0,0);

ALTER TABLE user ADD timecreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE invitation ADD inviteId integer;

select * from invitation;
select * from user;
SELECT * FROM user WHERE user.activation ='lock' ORDER BY RAND() LIMIT 5;

select * from groupmembers WHERE groupmembers.userId=255 AND groupmembers.familyGroupId=1881;

DELETE FROM user WHERE userId=289 limit 1;

select * from user order by timestamp desc limit 5;
SELECT * FROM user order by timecreated desc limit 5;
SELECT * FROM user ORDER BY RAND() LIMIT 5;
select * from familygroup;
select * from groupmembers;
DELETE FROM groupmembers WHERE groupmembers.userId=222 AND groupmembers.familyGroupId=2057 LIMIT 1;
create table familygroup
(
   groupId integer not null,
   groupName varchar(32) not null,
   createById integer not null
);
insert into groupmembers (userId,familyGroupId) values (222,296);
insert into groupmembers (userId,familyGroupId) values (206,1881);
DELETE FROM user WHERE user.userId=603 limit 1;
select * from familygroup where groupId=2870;
select * from groupmembers;
select * from parentnotification;


create table parentnotification
(
   notificationId integer not null,
   userId integer not null,
   parentFirstName varchar(25) not null,
   parentLastName varchar(25) not null
);

insert into parentnotification (notificationId , userId,parentFirstName, parentLastName) values (2215,650,'Lois','Lane');
DELETE FROM parentnotification WHERE parentnotification.userId=650 AND parentnotification.parentFirstName='Clark' AND parentnotification.parentLastName='Kent'limit 1; 

create table groupmembers
(
   userId integer not null,
   familyGroupId integer not null
);
