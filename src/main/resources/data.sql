-- Use this file to include the sentences to init the database (default values)
-- Insert here the default users, roles, etc.

--Default values for the table datasetType
insert into dataset_types(id, description, name) values(0, "Datasets uploaded to the platform by users with upload dataset permissions","systemdataset");
insert into dataset_types(id, description, name) values(1, "Datasets created by users mixing system datasets","userdataset");
 
--Admin password 12admin34
insert into user(username, confirmed_account, email, hash, name, password, photo, surname) values ('admin',b'1','onlinepreprocessor@gmail.com','system_default','System Administrator','$2a$10$3Gfa1K4Te7xeE4s8cvOhnecyN7v.iai4GXkhlrX1JXokt1PLHrKYi',null,'');
insert into permission(id, name, description) values (1,"canView", "Users can: view system datasets, view protected datasets and request permissions");
insert into permission(id, name, description) values (2,"canCreateCorpus", "Users can: view system datasets, view protected datasets, view and create their own datasets by mixing system datasets and requesting permissions");
insert into permission(id, name, description) values (3,"canUpload", "Users can: view system datasets, view protected datasets, view and create their own datasets by mixing those in the system. They can also upload system datasets and request permissions");
insert into permission(id, name, description) values (4,"canAdminister", "grant all privileges");
insert into user_perm(user,perm_id) values ("admin",4);