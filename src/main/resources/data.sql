INSERT INTO frontuser
  (id, active, gananciasybienesp, name, password, province, responsable_inscripto, roles, user_name)
  VALUES
  (1, 1, 1, 'Enrique Alonso', 'e10adc3949ba59abbe56e057f20f883e', 23, 1, 'USER', 'alonso.em@gmail.com'),
  (2, 1, 1, 'Leandro Platero', 'e10adc3949ba59abbe56e057f20f883e', 1, 0, 'USER', 'ljplatero@gmail.com'),
  (3, 1, 1, 'User Tierra del fuego', 'e10adc3949ba59abbe56e057f20f883e', 20, 1, 'USER', 'user@alec.com'),
  (4, 1, 0, 'User No responsable inscripto', 'e10adc3949ba59abbe56e057f20f883e', 1, 0, 'USER', 'userNRI@alec.com'),
  (5, 1, 0, 'User No ganancias', 'e10adc3949ba59abbe56e057f20f883e', 5, 1, 'USER', 'userNG@alec.com'),
  (6, 1, 0, 'API User', '1234', 0, 0, 'USER', 'apiuser@alec.com'),
  (7, 1, 0, 'User No ganancias Tierra del fuego', 'e10adc3949ba59abbe56e057f20f883e', 20, 0, 'USER', 'userNGTF@alec.com');

  --Alicuotas

INSERT INTO rate (id,name,rate) VALUES (1, 'IVA 21%', 21);
INSERT INTO rate (id,name,rate) VALUES (2, 'PAIS 8%', 8);
INSERT INTO rate (id,name,rate) VALUES (3, 'PAIS 30%', 30);
INSERT INTO rate (id,name,rate) VALUES (4, 'IVA 10,5%', 10.5);
INSERT INTO rate (id,name,rate) VALUES (5, 'IVA EXTERIOR', 21);


INSERT INTO broker (id, name,owner_id,is_public) VALUES (1,'Pago Servicios Digitales en el Exterior',1,1);

INSERT INTO tax (id,broker_id, name,url) VALUES (1,1,'Impuesto país', 'https://www.afip.gob.ar/impuesto-pais/ayuda/normativa.asp');
INSERT INTO tax (id,broker_id, name,url) VALUES (2,1,'IVA servicios digitales internacionales','https://www.afip.gob.ar/iva/servicios-digitales/concepto.asp');

insert into broker_taxes (broker_id, taxes_id) values (1, 1);
insert into broker_taxes (broker_id, taxes_id) values (1, 2);


insert into rule (id, tax_id, description, name, priority) values (1,1,'Verifica que apartado sea ninguno y aplica 0%', 'Sin Apartado', 1);
insert into rule_when_bis (rule_id,when_bis) values(1,'apartado==NOAPARTADO');
insert into rule_then_bis (rule_id,then_bis) values(1,'amount*30/100');

insert into tax_all_rules(tax_id,all_rules_id) values (1,1);



insert into rule (id,tax_id, description, name, priority) values (2,1,'Verifica que apartado sea igual A y aplica 21%', 'Es Apartado A', 1);
INSERT into rule_when_bis (rule_id,when_bis) values(2,'apartado==APARTADOA');
INSERT into rule_then_bis (rule_id,then_bis) values(2,'amount*8/100');

insert into tax_all_rules(tax_id,all_rules_id) values (1,2);

insert into rule (id,tax_id, description, name, priority) values (3,1, 'Verifica que apartado sea igual B y aplica 8%', 'Es Apartado B y monto menor a 10', 2);
INSERT into rule_when_bis (rule_id,when_bis) values(3,'apartado==APARTADOB');
INSERT into rule_when_bis (rule_id,when_bis) values(3,'amount<10');
INSERT into rule_then_bis (rule_id,then_bis) values(3,'amount*8/100');

insert into tax_all_rules(tax_id,all_rules_id) values (1,3);


insert into rule (id,tax_id, description, name, priority) values (4,1, 'Verifica que apartado sea igual B y aplica 30%', 'Es Apartado B y monto mayor a 10', 3);
INSERT into rule_when_bis (rule_id,when_bis) values(4,'apartado==APARTADOB');
INSERT into rule_when_bis (rule_id,when_bis) values(4,'amount>=10');
INSERT into rule_then_bis (rule_id,then_bis) values(4,'amount*30/100');

insert into tax_all_rules(tax_id,all_rules_id) values (1,4);



insert into rule (id,tax_id, description, name, priority) values (5,2,'Verifica que apartado sea ninguno y aplica 0%', 'Sin Apartado', 1);
insert into rule_when_bis (rule_id,when_bis) values(5,'apartado==NOAPARTADO');
insert into rule_then_bis (rule_id,then_bis) values(5,'0');

insert into tax_all_rules(tax_id,all_rules_id) values (2,5);


insert into rule (id,tax_id, description, name, priority) values (6,2,'Verifica que Si el usuario es de Tierra del fuego no aplica impuesto.', 'Es de tierra del fuego', 1);
insert into rule_when_bis (rule_id,when_bis) values(6,'province==TIERRA_DEL_FUEGO');
insert into rule_then_bis (rule_id,then_bis) values(6,'0');

insert into tax_all_rules(tax_id,all_rules_id) values (2,6);


insert into rule (id,tax_id, description, name, priority) values (7,2,'Verifica que Si el usuario es RI no aplica impuesto.', 'Es Responsable Inscripto', 2);
insert into rule_when_bis (rule_id,when_bis) values(7,'isEnrolledResponsable');
insert into rule_then_bis (rule_id,then_bis) values(7,'0');

insert into tax_all_rules(tax_id,all_rules_id) values (2,7);


insert into rule (id,tax_id, description, name, priority) values (8,2,'Verifica que si el apartado es A y aplica 21%.', 'Apartado A IVA', 3);
insert into rule_when_bis (rule_id,when_bis) values(8,'apartado==APARTADOA');
insert into rule_then_bis (rule_id,then_bis) values(8,'amount*21/100');

insert into tax_all_rules(tax_id,all_rules_id) values (2,8);


insert into rule (id,tax_id, description, name, priority) values (9,2,'Verifica que si el apartado es B y monto >= 10 aplica 0%.', 'Apartado B mayor a 10', 3);
insert into rule_when_bis (rule_id,when_bis) values(9,'apartado==APARTADOB');
insert into rule_when_bis (rule_id,when_bis) values(9,'amount>=10');
insert into rule_then_bis (rule_id,then_bis) values(9,'0');

insert into tax_all_rules(tax_id,all_rules_id) values (2,9);



insert into rule (id,tax_id, description, name, priority) values (10,2,'Verifica que si el apartado es B y monto < 10 aplica 21%.', 'Apartado B menor a 10', 4);
insert into rule_when_bis (rule_id,when_bis) values(10,'apartado==APARTADOB');
insert into rule_when_bis (rule_id,when_bis) values(10,'amount<10');
insert into rule_then_bis (rule_id,then_bis) values(10,'amount*21/100');

insert into tax_all_rules(tax_id,all_rules_id) values (2,10);


INSERT INTO broker (id, name,owner_id,is_public) VALUES (2,'Gravamen de emergencia sobre premios',1,0);

INSERT INTO tax (id,broker_id, name,url) VALUES (3,2,'Gravamen', 'https://www.afip.gob.ar');

insert into broker_taxes (broker_id, taxes_id) values (2, 3);

insert into rule (id,tax_id, description, name, priority) values (11,3,'Aplica el 30% sobre el 90% del premio', 'gravamenPremios', 1);
insert into rule_when_bis (rule_id,when_bis) values(11,'1==1');
insert into rule_then_bis (rule_id,then_bis) values(11,'amount*90/100*30/100');

insert into tax_all_rules(tax_id,all_rules_id) values (3,11);



INSERT INTO tax (id, broker_id, name, url) VALUES (4, 1, 'Adelanto al impuesto a las Ganancias y los Bienes Personales', 'https://www.afip.gob.ar/regimen-devolucion-percepciones/percepcion/que-es.asp');
insert into broker_taxes (broker_id, taxes_id) values (1, 4);

insert into rule (id, tax_id, description, name, priority) values (12,4,'35% sobre el monto', 'petreaintaycinco', 1);

insert into rule_when_bis (rule_id,when_bis) values(12,'isPersonalAssets');
insert into rule_then_bis (rule_id,then_bis) values(12,'amount*35/100');

insert into tax_all_rules(tax_id,all_rules_id) values (4,12);


INSERT INTO tax (id, broker_id, name, url) VALUES (5, 1, 'Percepción IIBB a los servicios digitales del exterior', 'https://www.agip.gob.ar/normativa/resoluciones/2019/agip/-resolucion-n-312--agip--2019');
insert into broker_taxes (broker_id, taxes_id) values (1, 5);

insert into rule (id,tax_id, description, name, priority) values (13,5,'35% sobre el monto', 'treintaycinco', 1);

insert into rule_when_bis (rule_id,when_bis) values(13,'province==CABA');
insert into rule_then_bis (rule_id,then_bis) values(13,'amount*2/100');

insert into tax_all_rules(tax_id,all_rules_id) values (5,13);

--Facts

INSERT INTO group_fact (dtype,name,description,class_name) VALUES ('GroupClassFact','provinces','Provincias disponibles','ar.edu.unq.ttip.alec.backend.model.enumClasses.Province');
INSERT INTO group_fact (dtype,name,description,class_name) VALUES ('GroupClassFact','apartados','Apartados disponibles','ar.edu.unq.ttip.alec.backend.model.enumClasses.Apartado');



INSERT INTO group_fact (dtype, name,description) VALUES ('GroupFact','user','Usuario afectable al calculo');
INSERT INTO fact (dtype,name,description,fixed,type) VALUES ('Fact','province','Provincia del usuario',true,0);
INSERT INTO fact (dtype,name,description,fixed,type) VALUES ('Fact','isPersonalAssets','Tributa Bienes personales',true,0);
INSERT INTO fact (dtype,name,description,fixed,type) VALUES ('Fact','isEnrolledResponsable','Es Responsable inscripto',true,0);
insert into group_fact_facts (group_fact_name,facts_name) values ('user','province');
insert into group_fact_facts (group_fact_name,facts_name) values ('user','isPersonalAssets');
insert into group_fact_facts (group_fact_name,facts_name) values ('user','isEnrolledResponsable');


INSERT INTO group_fact (dtype,name,description) VALUES ('GroupFact','Core','Parametros basicos');
INSERT INTO fact (dtype,name,description,fixed,type) VALUES ('Fact','amount','Monto imponible',true,0);
INSERT INTO fact (dtype,name,description,fixed,type) VALUES ('Fact','apartado','Apartado afectado al calculo.',true,0);
insert into group_fact_facts (group_fact_name,facts_name) values ('Core','amount');
insert into group_fact_facts (group_fact_name,facts_name) values ('Core','apartado');


INSERT INTO group_fact (dtype,name,description) VALUES ('GroupFact','Rates','Parametros de tass');
INSERT INTO fact (dtype,name,description,fixed,type) VALUES ('RateFact','iva','Tasa Iva',false,1);
INSERT INTO fact (dtype,name,description,fixed,type) VALUES ('RateFact','pais8','Tasa pais 8%',false,1);
INSERT INTO fact (dtype,name,description,fixed,type) VALUES ('RateFact','pais30','Tasa pais 30%',false,1);
insert into rel_facts_rates (fk_fact,fk_rate) values ('iva',1);
insert into rel_facts_rates (fk_fact,fk_rate) values ('pais8',2);
insert into rel_facts_rates (fk_fact,fk_rate) values ('pais30',3);
insert into group_fact_facts (group_fact_name,facts_name) values ('Rates','iva');
insert into group_fact_facts (group_fact_name,facts_name) values ('Rates','pais8');
insert into group_fact_facts (group_fact_name,facts_name) values ('Rates','pais30');


INSERT INTO broker (id, name,owner_id,is_public) VALUES (3,'IVA 21%',1,1);

INSERT INTO tax (id,broker_id, name,url) VALUES (6,1,'Impuesto Iva 21%', 'https://www.afip.gob.ar/');

insert into broker_taxes (broker_id, taxes_id) values (3, 6);


insert into rule (id, tax_id, description, name, priority) values (14,6,'Aplica siempre el 21%', '21% Siempre', 1);
insert into rule_when_bis (rule_id,when_bis) values(14,'1==1');
insert into rule_then_bis (rule_id,then_bis) values(14,'amount*iva/100');

insert into tax_all_rules(tax_id,all_rules_id) values (6,14);

