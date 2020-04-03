INSERT INTO vets VALUES (1, 'James', 'Carter', '12345678A');
INSERT INTO vets VALUES (2, 'Helen', 'Leary', '51445568E');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas', '64412623P');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega', '78451239G');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens', '84512632Y');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins', '87445132Q');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '24576831A', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '51241523H', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '56143874J', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '45684633Q', '563 Friendly St.', 'Windsor', '6085553198');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '53614289Ñ', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '46852098Y', '105 N. Lake St.', 'Monona', '6085552654');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '53612474N', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '84769526Y', '345 Maple St.', 'Madison', '6085557683');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '63274264P', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '65514641W', '2335 Independence La.', 'Waunakee', '6085555487');

INSERT INTO pets VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO hotels VALUES (1, 'hotel1', '2020-05-10', '2020-05-12', 1);
INSERT INTO hotels VALUES (2, 'hotel2', '2020-06-18', '2020-06-20', 7);
INSERT INTO hotels VALUES (3, 'hotel3', '2020-10-25', '2020-10-25', 3);

INSERT INTO causes VALUES (1,'Cause1','This is a description',500,'Organization1');
INSERT INTO causes VALUES (2,'Cause2','This is a description',800,'Organization2');
INSERT INTO causes VALUES (3,'Cause3','This is a description',1000,'Organization3');
INSERT INTO causes VALUES (4,'Cause4','This is a description',1000,'Organization4');

INSERT INTO donations VALUES (1,'Donation1', '53.52', '2020-03-22', 'Antonio', 1);
INSERT INTO donations VALUES (2,'Donation2', '251.15', '2020-03-26','Francisco', 2);
INSERT INTO donations VALUES (3,'Donation3','61.81', '2020-03-29','Octavio', 1);
INSERT INTO donations VALUES (4,'Donation4', '824.13', '2020-02-02', 'Matilda', 3);
INSERT INTO donations VALUES (5,'Donation5','523.241', '2020-02-02', 'Rosa', 4);
