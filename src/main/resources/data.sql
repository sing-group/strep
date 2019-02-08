-- Use this file to include the sentences to init the database (default values)
-- Insert here the default users, roles, etc.

delete from user;
insert into user(username, confirmed_account, email, hash, name, password, photo, surname) values ('admin',b'1','onlinepreprocessor@gmail.com','system_default','System Administrator','12admin34',null,'');