
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (1,'Depresion','¿Sientes tranquilidad el día de hoy?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (2,'Depresion','¿Logras concentrarte en lo que haces?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (3,'Depresion','¿Te sientes con culpa hacia ti mismo?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (4,'Depresion','¿Crees merecer un castigo?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (5,'Depresion','¿Te alimentaste bien y con ganas el día de hoy?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (6,'Depresion','¿Hoy has tomado decisiones sin analizar demasiado?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (7,'Depresion','¿Dormiste bien anoche?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (8,'Depresion','¿Tardas más de lo normal haciendo tus labores rutinarias?',1,1);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (9,'Depresion','¿Sientes tener una meta?',1,1);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (10,'Depresion','¿Sientes querer estar solo?',1,1);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (11,'Ansiedad','¿Logras concentrarte más de 5 minutos en una actividad el día de hoy?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (12,'Ansiedad','¿Has estado estable emocionalmente el día de hoy?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (13,'Ansiedad','¿Tuviste momento de inquietud el día de hoy?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (14,'Ansiedad','¿Anoche pudiste dormir temprano?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (15,'Ansiedad','¿Tienes el día de hoy la mente tranquila y controlada?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (16,'Ansiedad','¿Has fumado, bebido alcohol o ingerido sustancias alucinógenas para calmar tu mente?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (17,'Ansiedad','¿Has tenido ganas o llorado el día de hoy?',1,0);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (18,'Ansiedad','¿Sientes angustia?',1,1);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (19,'Ansiedad','¿Tienes tensión en alguna parte del cuerpo?',1,1);
INSERT INTO pregunta (`id`,`area`,`pregunta`,`prediccion`,`prioridad`) VALUES (20,'Ansiedad','¿Has pensado en varios temas sin poder tomar una decisión de qué quieres?',1,1);


INSERT INTO psicologo (`id`,`email`,`identificacion`,`nombre`) VALUES (1,'alexander98o2@gmail.com',1094966562,'Cristian Cardenas');

INSERT INTO paciente (`id`,`descripcion`,`direccion`,`email`,`fecha_ingreso`,`fecha_nacimiento`,`identificacion`,`nombre`,`numero`,`psicologo_id`,`ansiedad`,`depresion`,`facultad`,`semestre`,`edad`,`ciudad`,`diagnostico`,`programa`,`prioridad`) VALUES (2,NULL,NULL,'linamcd@gmail.com','2021-06-04',NULL,41960132,'Lina Carmona',3113883375,1,1,1,NULL,10,51,NULL,1,'Comunicación Social',1);
INSERT INTO paciente (`id`,`descripcion`,`direccion`,`email`,`fecha_ingreso`,`fecha_nacimiento`,`identificacion`,`nombre`,`numero`,`psicologo_id`,`ansiedad`,`depresion`,`facultad`,`semestre`,`edad`,`ciudad`,`diagnostico`,`programa`,`prioridad`) VALUES (8,NULL,NULL,'cacardenasm@uqvirtual.edu.co','2021-09-16','1998-01-25',1094966566,'Alexander Montoya',NULL,1,0,1,NULL,10,23,NULL,1,'Ingenieria de Sistemas y Computación',0);
