-- AQUI SE INSERTARAN TODOS LOS DATOS EN LA BD


insert into espectaculo values(NULL,'Depor-Celta','Deporte','Derbi Gallego',10);
insert into espectaculo values(NULL,'Titanic','Cine','Amor en un barco',10);
insert into espectaculo values(NULL,'Concierto_Justin_Bierber','Musica','Vuelta a los escenarios de Justin',10);
insert into espectaculo values(NULL, 'Circo del Sol','Exibicion','Atletas haciendo piruetas',10);
insert into espectaculo values(NULL,'Los vengadores','Cine','Tony Stark vuelve a la accion',10);



insert into recinto values(NULL,'Balaidos','Campo Futbol');
insert into recinto values(NULL,'Vistalegre','Multiespacio camaleónico ');
insert into recinto values(NULL,'Cines Plaza E','Cines');
insert into recinto values(NULL, 'Wanda_Metropolitano','Campo Futbol');



insert into grada values(1,NULL,'Preferencia',3);
insert into grada values(1,NULL,'Rio Alto',3);
insert into grada values(2,NULL,'Palco',3);
insert into grada values(2,NULL,'Gallinero',3);
insert into grada values(3,NULL,'Baja',3);
insert into grada values(3,NULL,'Alta',3);
insert into grada values(4,NULL,'Gol',3);
insert into grada values(4,NULL,'Tribuna',3);

insert into localidad values(null,1,1,'libre' );
insert into localidad values(null,1,1,'libre' );
insert into localidad values(null,1,1,'libre' );
insert into localidad values(null,1,2,'libre' );
insert into localidad values(null,1,2,'libre' );
insert into localidad values(null,1,2,'libre' );
insert into localidad values(null,2,3,'libre' );
insert into localidad values(null,2,3,'libre' );
insert into localidad values(null,2,3,'libre' );
insert into localidad values(null,2,4,'libre' );
insert into localidad values(null,2,4,'libre' );
insert into localidad values(null,2,4,'libre' );
insert into localidad values(null,3,5,'libre' );
insert into localidad values(null,3,5,'libre' );
insert into localidad values(null,3,5,'libre' );
insert into localidad values(null,3,6,'libre' );
insert into localidad values(null,3,6,'libre' );
insert into localidad values(null,3,6,'libre' );
insert into localidad values(null,4,7,'libre' );
insert into localidad values(null,4,7,'libre' );
insert into localidad values(null,4,7,'libre' );
insert into localidad values(null,4,8,'libre' );
insert into localidad values(null,4,8,'libre' );
insert into localidad values(null,4,8,'libre' );





insert into cliente values('41319498T','Jesus Lucin','192782648267182','Jubilado');
insert into cliente values('60374527G','Jose Silvosa','192856391754850','Adulto');
insert into cliente values('13239949G','Pedro Martinez','6583756229861523','Parado');
insert into cliente values('12341234R','Samuel Perez','6572845215117582','infantil');
insert into cliente values('12345678H','Daniel Novo','3759728591759173','Administrador');

insert into participante(id_participante,nombre_participante) values
 (null,'Aspas'),
 (null, 'Jack'),
 (null,'Rose'),
 (null,'Justin Bieber'),
 (null,'Jue Lee'),
 (null,'Chin Lee'),
 (null,'Tony Stark'),
 (null,'Spiderman');
 
insert into participaEn(id_participante,id_espectaculo) values
(1,1),
(2,2),
(3,2),
(4,3),
(5,4),
(6,4),
(7,5),
(8,5);



