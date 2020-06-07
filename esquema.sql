-- BORRAMOS LA BD SI EXISTE
drop database if exists proyecto25;
create database proyecto25;
use proyecto25;
 
 
-- CREAMOS LAS TABLAS
 
 
create table espectaculo(
id_espectaculo int not null auto_increment ,
nombre varchar(40),
tipo varchar(20),
descripcion varchar(200)not null,
penalizacion int,
primary key(id_espectaculo)
);
 
 
create table recinto(
id_recinto int not null auto_increment primary key,
nombre varchar(40),
tipo varchar(30)
);
 
create table participante(
 id_participante int not null auto_increment,
 nombre_participante varchar(100)not null,
 primary key(id_participante)

);
create table participaEn(
id_participante int not null,
id_espectaculo int not null,
foreign key (id_participante) references participante(id_participante),
foreign key (id_espectaculo) references espectaculo(id_espectaculo) 
  );
 

 
 
create table evento(
id_evento int not null auto_increment,
id_espectaculo int not null,
id_recinto int not null,
fecha timestamp not null,
estado varchar(20) not null,
t1 int,
t2 int,
t3 int,

Entradas_infantil boolean,
Entradas_parado boolean,
Entradas_adulto boolean,
Entradas_jubilado boolean,
primary key(id_evento),
foreign key(id_espectaculo) references espectaculo(id_espectaculo),
foreign key(id_recinto) references recinto(id_recinto)
);
 
 
create table cliente(
DNI varchar(15) primary key,
nombreCompleto varchar(50) not null,
tarjeta_credito varchar(30) not null,
tipo_usuario varchar(50) not null
);

 
 
create table grada(
id_recinto int not null,
id_grada int not null auto_increment,
nombre_grada varchar(40),
N_localidades int,
foreign key(id_recinto) references recinto(id_recinto),
primary key(id_grada)
);
 
create table define(
id_evento int not null,
id_grada int not null,
P_Jubilado int,
P_Adulto int,
P_Parado int,
P_Infantil int,
foreign key(id_evento) references evento(id_evento),
foreign key(id_grada) references grada(id_grada),
primary key(id_evento,id_grada)

);
 
 
 
create table localidad(
id_localidad int not null auto_increment,
id_recinto int not null,
id_grada int not null,
estado varchar(30) default 'libre',
foreign key (id_recinto) references recinto(id_recinto),
foreign key (id_grada) references grada(id_grada),
primary key(id_localidad)
);

Create table reserva(
id_reserva int not null auto_increment,
DNI varchar(20),
id_evento int not null,
id_grada int not null,
id_localidad int not null,
FechaRealizacion timestamp not null,
estado varchar(40) not null,
foreign key (id_evento) references evento(id_evento),
foreign key (DNI) references cliente(DNI),
foreign key (id_grada) references grada(id_grada),
foreign key (id_localidad) references localidad(id_localidad),
primary key (id_reserva)

);

