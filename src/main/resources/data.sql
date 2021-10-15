INSERT INTO frontuser
  (id, active, gananciasybienesp, name, password, province, responsable_inscripto, roles, user_name)
  VALUES
  (1, 1, 1, 'Enrique Alonso', 'e10adc3949ba59abbe56e057f20f883e', 23, 1, 'USER', 'alonso.em@gmail.com'),
  (2, 1, 1, 'Leandro Platero', 'e10adc3949ba59abbe56e057f20f883e', 1, 0, 'USER', 'ljplatero@gmail.com'),
  (3, 1, 1, 'User Tierra del fuego', 'e10adc3949ba59abbe56e057f20f883e', 20, 1, 'USER', 'user@alec.com'),
  (4, 1, 1, 'User No responsable inscripto', 'e10adc3949ba59abbe56e057f20f883e', 12, 0, 'USER', 'userNRI@alec.com'),
  (5, 1, 0, 'User No ganancias', 'e10adc3949ba59abbe56e057f20f883e', 5, 1, 'USER', 'userNG@alec.com'),
  (6, 1, 0, 'API User', '1234', 0, 0, 'USER', 'apiuser@alec.com');

INSERT INTO broker (id, name) VALUES (1,'Broker pagos exterior');

INSERT INTO tax (id, name,url) VALUES (1,'IMPUESTO PAIS', 'https://www.afip.gob.ar');
INSERT INTO tax (id, name,url) VALUES (2,'IVA EXTERIOR','https://www.arba.gob.ar');

insert into broker_taxes (broker_id, taxes_id) values (1, 1);
insert into broker_taxes (broker_id, taxes_id) values (1, 2);


insert into rule (id, description, name, priority) values (1,'Verifica que apartado sea ninguno y aplica 0%', 'Sin Apartado', 1);
insert into condition_action (id,value) values(1,'apartado=apartadoClass.NOAPARTADO');
insert into rule_when (rule_id,when_id) values (1,1);
insert into condition_action (id,value) values(2,'result.value=amount*30/100;');
INSERT into rule_then (rule_id,then_id) values (1,2);

insert into tax_all_rules(tax_id,all_rules_id) values (1,1);


insert into rule (id, description, name, priority) values (2,'Verifica que apartado sea igual A y aplica 21%', 'Es Apartado A', 1);
INSERT into condition_action (id,value) values(3,'apartado=apartadoClass.APARTADOA');
INSERT into rule_when (rule_id,when_id) values (2,3);
INSERT into condition_action (id,value) values(4,'result.value=amount*8/100;');
INSERT into rule_then (rule_id,then_id) values (2,4);

insert into tax_all_rules(tax_id,all_rules_id) values (1,2);

insert into rule (id, description, name, priority) values (3, 'Verifica que apartado sea igual B y aplica 8%', 'Es Apartado B y monto menor a 10', 2);
INSERT into condition_action (id,value) values(5,'apartado=apartadoClass.APARTADOB');
INSERT into condition_action (id,value) values(6,'amount<10');
INSERT into rule_when (rule_id,when_id) values (3,5);
INSERT into rule_when (rule_id,when_id) values (3,6);
INSERT into condition_action (id,value) values(7,'result.value=amount*8/100;');
INSERT into rule_then (rule_id,then_id) values (3,7);

insert into tax_all_rules(tax_id,all_rules_id) values (1,3);


insert into rule (id, description, name, priority) values (4, 'Verifica que apartado sea igual B y aplica 30%', 'Es Apartado B y monto mayor a 10', 3);
INSERT into condition_action (id,value) values(8,'apartado=apartadoClass.APARTADOB');
INSERT into condition_action (id,value) values(9,'amount>=10');
INSERT into rule_when (rule_id,when_id) values (4,8);
INSERT into rule_when (rule_id,when_id) values (4,9);
INSERT into condition_action (id,value) values(10,'result.value=amount*30/100;');
INSERT into rule_then (rule_id,then_id) values (4,10);


insert into tax_all_rules(tax_id,all_rules_id) values (1,4);



insert into rule (id, description, name, priority) values (5,'Verifica que apartado sea ninguno y aplica 0%', 'Sin Apartado', 1);
insert into condition_action (id,value) values(11,'apartado=apartadoClass.NOAPARTADO');
insert into rule_when (rule_id,when_id) values (5,11);
insert into condition_action (id,value) values(12,'result.value=0;');
INSERT into rule_then (rule_id,then_id) values (5,12);

insert into tax_all_rules(tax_id,all_rules_id) values (2,5);


insert into rule (id, description, name, priority) values (6,'Verifica que Si el usuario es de Tierra del fuego no aplica impuesto.', 'Es de tierra del fuego', 1);
insert into condition_action (id,value) values(13,'user.getProvince()==provinceClass.TIERRA_DEL_FUEGO');
insert into rule_when (rule_id,when_id) values (6,13);
insert into condition_action (id,value) values(14,'result.value=0;');
INSERT into rule_then (rule_id,then_id) values (6,14);

insert into tax_all_rules(tax_id,all_rules_id) values (2,6);


insert into rule (id, description, name, priority) values (7,'Verifica que Si el usuario es RI no aplica impuesto.', 'Es Responsable Inscripto', 2);
insert into condition_action (id,value) values(15,'user.isResponsableInscripto()');
insert into rule_when (rule_id,when_id) values (7,15);
insert into condition_action (id,value) values(16,'result.value=0;');
INSERT into rule_then (rule_id,then_id) values (7,16);

insert into tax_all_rules(tax_id,all_rules_id) values (2,7);


insert into rule (id, description, name, priority) values (8,'Verifica que si el apartado es A y aplica 21%.', 'Apartado A IVA', 3);
insert into condition_action (id,value) values(17,'apartado=apartadoClass.APARTADOA');
insert into rule_when (rule_id,when_id) values (8,17);
insert into condition_action (id,value) values(18,'result.value=amount*21/100;');
INSERT into rule_then (rule_id,then_id) values (8,18);

insert into tax_all_rules(tax_id,all_rules_id) values (2,8);


insert into rule (id, description, name, priority) values (9,'Verifica que si el apartado es B y monto >= 10 aplica 0%.', 'Apartado B mayor a 10', 3);
insert into condition_action (id,value) values(19,'apartado=apartadoClass.APARTADOB');
insert into rule_when (rule_id,when_id) values (9,19);
insert into condition_action (id,value) values(20,'amount>=10');
insert into rule_when (rule_id,when_id) values (9,20);
insert into condition_action (id,value) values(21,'result.value=0;');
INSERT into rule_then (rule_id,then_id) values (9,21);

insert into tax_all_rules(tax_id,all_rules_id) values (2,9);



insert into rule (id, description, name, priority) values (10,'Verifica que si el apartado es B y monto < 10 aplica 21%.', 'Apartado B menor a 10', 4);
insert into condition_action (id,value) values(22,'apartado=apartadoClass.APARTADOB');
insert into rule_when (rule_id,when_id) values (10,22);
insert into condition_action (id,value) values(23,'amount<10');
insert into rule_when (rule_id,when_id) values (10,23);
insert into condition_action (id,value) values(24,'result.value=amount*21/100;');
INSERT into rule_then (rule_id,then_id) values (10,24);

insert into tax_all_rules(tax_id,all_rules_id) values (2,10);






INSERT INTO broker (id, name) VALUES (2,'Broker Gravamen de emergencia sobre premios');

INSERT INTO tax (id, name,url) VALUES (3,'Gravamen', 'https://www.afip.gob.ar');

insert into broker_taxes (broker_id, taxes_id) values (2, 3);

insert into rule (id, description, name, priority) values (11,'Aplica el 30% sobre el 90% del premio', 'gravamenPremios', 1);
insert into condition_action (id,value) values(25,'1==1');
insert into rule_when (rule_id,when_id) values (11,25);
insert into condition_action (id,value) values(26,'result.value=amount*90/100*30/100;');
INSERT into rule_then (rule_id,then_id) values (11,26);

insert into tax_all_rules(tax_id,all_rules_id) values (3,11);



INSERT INTO tax (id, name) VALUES (4,'Adelanto al impuesto a las Ganancias y los Bienes Personales');
insert into broker_taxes (broker_id, taxes_id) values (1, 4);

insert into rule (id, description, name, priority) values (12,'35% sobre el monto', 'petreaintaycinco', 1);

insert into condition_action (id,value) values(27,'user.isGananciasYBienesP()');
insert into rule_when (rule_id,when_id) values (12,27);

insert into condition_action (id,value) values(28,'result.value=amount*35/100;');
INSERT into rule_then (rule_id,then_id) values (12,28);

insert into tax_all_rules(tax_id,all_rules_id) values (4,12);


INSERT INTO tax (id, name) VALUES (5,'Percepción IIBB a los servicios digitales del exterior');
insert into broker_taxes (broker_id, taxes_id) values (1, 5);

insert into rule (id, description, name, priority) values (13,'35% sobre el monto', 'petreaintaycinco', 1);

insert into condition_action (id,value) values(29,'user.getProvince()==apartadoClass.CABA');
insert into rule_when (rule_id,when_id) values (13,29);

insert into condition_action (id,value) values(30,'result.value=amount*2/100;');
INSERT into rule_then (rule_id,then_id) values (13,30);

insert into tax_all_rules(tax_id,all_rules_id) values (5,13);



--Alicuotas

INSERT INTO rate (id,name,rate) VALUES (1, 'IVA 21%', 21);
INSERT INTO rate (id,name,rate) VALUES (2, 'PAIS 8%', 8);
INSERT INTO rate (id,name,rate) VALUES (3, 'PAIS 30%', 30);
INSERT INTO rate (id,name,rate) VALUES (4, 'IVA 10,5%', 10.5);
INSERT INTO rate (id,name,rate) VALUES (5, 'IVA EXTERIOR', 21);


--Facts

INSERT INTO fact (dtype, name,class_name,fixed) VALUES ('ClassFact', 'provinceClass','ar.edu.unq.ttip.alec.backend.model.enumClasses.Province',false);
INSERT INTO fact (dtype, name,class_name,fixed) VALUES ('ClassFact','apartadoClass','ar.edu.unq.ttip.alec.backend.model.enumClasses.LACHOTAApartado',false);
INSERT INTO fact (dtype,name,fixed) VALUES ('Fact','amount',true);
INSERT INTO fact (dtype,name,fixed) VALUES ('Fact','result',true);
INSERT INTO fact (dtype,name,fixed) VALUES ('Fact','apartado',true);
INSERT INTO fact (dtype,name,fixed) VALUES ('Fact','user',true);
