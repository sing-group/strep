-- Use this file to include the sentences to init the database (default values)
-- Insert here the default users, roles, etc.


 insert into dataset (name, author, description, pipeline, doi, access, language, type, upload_date, spam_percentage, ham_percentage) values("datasetprueba1", "Yo", "Este es un dataset de prueba para mostrar en los datasets publicos","dfagergvrad", "dfgaerdgbvtrzd", "public", "spanish", "systemdataset", '1000-01-01 00:00:00', 30, 70);
 insert into dataset (name, author, description, pipeline, doi, access, language, type, upload_date, spam_percentage, ham_percentage) values("systemdataset2", "ismav10", "Este es un dataset de prueba para mostrar en los datasets publicos","dfagergvrad", "dfgaerdgbvtrzd", "protected", "spanish", "systemdataset", '1000-01-01 00:00:00', 30, 70);
 insert into dataset (name, author, description, pipeline, doi, access, language, type, upload_date, spam_percentage, ham_percentage) values("systemdataset3", "Yo", "Este es un dataset de prueba para mostrar en los datasets publicos","dfagergvrad", "dfgaerdgbvtrzd", "public", "spanish", "systemdataset", '1000-01-01 00:00:00', 30, 70);
 insert into dataset (name, author, description, pipeline, doi, access, language, type, upload_date, spam_percentage, ham_percentage) values("systemdataset4", "Yo", "Este es un dataset de prueba para mostrar en los datasets privados","dfagergvrad", "dfgaerdgbvtrzd", "private", "spanish", "systemdataset", '1000-01-01 00:00:00', 30, 70);
 
 insert into file(id, path, type, language, date, extension) values(1, "/home/ismael/Desarrollo/datasets/datasetprueba1/_ham_/emailprueba1","ham","spanish",'1000-01-01 00:00:00',".eml");
 insert into file(id, path, type, language, date, extension) values(2, "/home/ismael/Desarrollo/datasets/datasetprueba1/_ham_/emailprueba2","ham","spanish",'1000-01-01 00:00:00',".eml");
 insert into file(id, path, type, language, date, extension) values(3, "/home/ismael/Desarrollo/datasets/datasetprueba1/_spam_/emailspam1","spam","spanish",'1000-01-01 00:00:00',".eml");
 
 insert into dataset_files(dataset_name, file_id) values("datasetprueba1",1);
 insert into dataset_files(dataset_name, file_id) values("datasetprueba1",2);
 insert into dataset_files(dataset_name, file_id) values("datasetprueba1",3);
 
insert into user(username, confirmed_account, email, hash, name, password, photo, surname) values ('admin',b'1','onlinepreprocessor@gmail.com','system_default','System Administrator','12admin34',null,'');
insert into permission(id, name, description) values (1,"canView", "grant all privileges");
insert into permission(id, name, description) values (2,"canUpload", "grant all privileges");
insert into permission(id, name, description) values (3,"canCreateCorpus", "grant all privileges");
insert into permission(id, name, description) values (4,"canAdminister", "grant all privileges");
insert into user_perm(user,perm_id) values ("admin",4);