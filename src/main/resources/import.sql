INSERT INTO region(name) VALUES ('Praha 1');
INSERT INTO region(name) VALUES ('Praha 2');
INSERT INTO region(name) VALUES ('Praha 3');
INSERT INTO region(name) VALUES ('Praha 4');
INSERT INTO region(name) VALUES ('Praha 10');
INSERT INTO region(name) VALUES ('Liberec');

INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('Štěpánská 795/65, 110 00 Praha-Praha 1, Česká republika', 'Díra na ulici', '2014-10-22 11:41:04.777', 50.081272, 14.426791, 'IN_PROGRESS', 1);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('Nuselská 867/88, 140 00 Praha-Praha 4, Česká republika', 'zlomená značka', '2014-10-24 17:31:04.777', 50.058651, 14.448895, 'IN_PROGRESS', 4);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('Rumunská 1720/12, 120 00 Praha-Praha 2, Česká republika', 'Bordel u popelnic', '2014-10-20 09:41:04.777', 50.073835, 14.431177, 'NEW', 2);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('náměstí Svatopluka Čecha 1368/11, 101 00 Praha-Praha 10, Česká republika', 'Bezdomovci tu pijí alkohol', '2014-10-20 06:11:04.777', 50.068898, 14.460467, 'NEW', 5);

INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('Stará esta s18', 'Porouchaná sušučka', '2014-10-22 12:41:04.777', 50.081382, 14.427800, 'IN_PROGRESS', 1);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('Na poříčí hošánci', 'vyfocené kuřátko co rádo spinká a slintá', '2014-10-22 13:41:04.777', 50.081272, 14.428791, 'IN_PROGRESS', 1);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('U suchdola 5', 'Nějaká bezejmenná škoda', '2014-10-22 14:41:04.777', 50.081492, 14.426810, 'IN_PROGRESS', 1);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('Borovského ulice 154', 'Hokejisti zničili stadion', '2014-10-22 15:41:04.777', 50.081500, 14.429820, 'IN_PROGRESS', 1);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('Adresa na kraji města', 'Ptačí výkaly na ulici', '2014-10-22 16:41:04.777', 50.081610, 14.430830, 'IN_PROGRESS', 1);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('Bitevního pole 16', 'Černá skládka v centru města!!!', '2014-10-22 17:41:04.777', 50.081720, 14.431840, 'IN_PROGRESS', 1);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('ADresa na konci svězta', 'Nejaký trouba vyndal kostky z chodníku', '2014-10-22 18:41:04.777', 50.081830, 14.432850, 'NEW', 1);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('Příčná ulice 19', 'Jen si z vás dělám srandu saláti', '2014-10-22 19:41:04.777', 50.081940, 14.433860, 'NEW', 1);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('U Olivandera 1', 'Rozbitá výloha souukromého obchodu', '2014-10-22 20:41:04.777', 50.082000, 14.434870, 'NEW', 1);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('Nádraží vršovice', 'Skákal pes přes oves', '2014-10-22 21:41:04.777', 50.082100, 14.423580, 'NEW', 1);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('I.P.Pavlova bašta', 'Tahle aplikace je strašný brak', '2014-10-22 22:41:04.777', 50.082200, 14.436890, 'NEW', 1);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('náměstí u tří lvů', 'Rozpustili se voskové figuríny', '2014-10-22 23:41:04.777', 50.082300, 14.437900, 'NEW', 1);
INSERT INTO incident(address, title, insertedtime, latitude, longitude, state, region_id) VALUES ('náměstí Svatopluka Čecha 1368/11, 101 00 Praha-Praha 10, Česká republika', 'Lavička je uplně na kaši, nechápu že to ještě nikdo nevyřešil. Chodím kolem toho každý den je to fakt hrůza', '2014-10-23 01:41:04.777', 50.0812390, 14.426910, 'NEW', 1);

INSERT INTO person(username, password, name, surname, role, region_id) VALUES ('spravce@eos.cz', 'ypeBEsobvcr6wjGzmiPcTaeG7/gUfE5yuYB3ha/uSLs=', 'Správce', 'Systému', 'SUPER_ADMIN', null);
INSERT INTO person(username, password, name, surname, role, region_id) VALUES ('region@eos.cz', 'ypeBEsobvcr6wjGzmiPcTaeG7/gUfE5yuYB3ha/uSLs=', 'Region', 'Admin', 'REGION_ADMIN', 1);
INSERT INTO person(username, password, name, surname, role, region_id) VALUES ('officer@eos.cz', 'ypeBEsobvcr6wjGzmiPcTaeG7/gUfE5yuYB3ha/uSLs=', 'Officer', 'Úředník', 'OFFICER', 1);

INSERT INTO message(insertedtime, text, person_id, incident_id) VALUES('2014-10-25 19:30:04.777', 'Děkujeme za nahlášení, koukneme se na to.', 2, 1);
INSERT INTO message(insertedtime, text, person_id, incident_id) VALUES('2014-10-26 18:30:04.777', 'Bylo vyřešeno snad.', 2, 1);
INSERT INTO message(insertedtime, text, person_id, incident_id) VALUES('2014-10-25 20:32:04.777', 'Oprava bylo to vyřešeno až nyní. Omlouváme se za komplikace', 3, 1);

INSERT INTO message(insertedtime, text, person_id, incident_id) VALUES('2014-10-25 19:30:04.777', 'Děkujeme za nahlášení této vážné škody, koukneme se na to.', 2, 2);
INSERT INTO message(insertedtime, text, person_id, incident_id) VALUES('2014-10-26 18:30:04.777', 'Je to oříšek, lavička asi nepatří městu', 2, 2);
INSERT INTO message(insertedtime, text, person_id, incident_id) VALUES('2014-10-25 20:32:04.777', 'Oprava bylo to vyřešeno až nyní. Omlouváme se za komplikace', 3, 2);


INSERT INTO comment(insertedtime, text, person_id, incident_id) VALUES('2014-10-25 21:10:04.777', 'Ta značka je OK, kdoví kdo to nahlašoval', 2, 1);
INSERT INTO comment(insertedtime, text, person_id, incident_id) VALUES('2014-10-26 15:10:04.777', 'Zavírám to a řeknu že je to vyřešené', 1, 1);
INSERT INTO comment(insertedtime, text, person_id, incident_id) VALUES('2014-10-25 15:49:04.777', 'Uff tohle máme za trest', 3, 5);
