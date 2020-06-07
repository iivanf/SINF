delimiter //
drop procedure if exists crear_evento//
create procedure crear_evento(IN id_espectaculo varchar(50),IN id_recinto varchar(40),IN fecha varchar(50),IN t1 int, IN t2 int, IN t3 int, IN ent_infantil int, IN ent_adulto int, IN ent_parado int,IN ent_jubilado int)
BEGIN
insert into evento values(NULL,id_espectaculo,id_recinto,fecha,'abierto',t1,t2,t3,ent_infantil,ent_adulto,ent_parado,ent_jubilado);
END//



drop procedure if exists Definir_Precios_Evento_Grada//
create procedure Definir_Precios_Evento_Grada(in id_eventoIn int,in id_gradaIn int,in P_Jubilado int,in P_Adulto int,in P_Parado int,in P_Infantil int)
BEGIN
declare id_recintoAUX int;
select  grada.id_recinto into id_recintoAUX from grada,evento where grada.id_grada=id_gradaIn and evento.id_recinto=grada.id_recinto and evento.id_evento=id_eventoIn;

	if (id_recintoAUX is null)   then SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "La GRADA NO PERTENECE AL EVENTO";
  else
insert into define values (id_eventoIn,id_gradaIn,P_Jubilado,P_adulto,P_Parado,P_Infantil);
  end if;
end//



drop procedure if exists Precios_Grada_Evento//
create procedure Precios_Grada_Evento(in id_evento int)
BEGIN
Select id_grada,P_Jubilado,P_Adulto,P_Parado,P_Infantil from define where define.id_evento=id_evento;
end//




drop procedure if exists Participante_Evento//
create procedure Participante_Evento(in id_eventoAux int)
Begin
declare id_espectaculoAux int;

select evento.id_espectaculo into id_espectaculoAux from evento where evento.id_evento=id_eventoAux;
select participante.nombre_participante from participante inner join participaEn
	on participaEn.id_participante=participante.id_participante
	inner join espectaculo
	on espectaculo.id_espectaculo=participaEn.id_espectaculo
	where espectaculo.id_espectaculo=id_espectaculoAux;
end//




drop procedure if exists Obtener_Eventos//
create procedure Obtener_Eventos(in id_recintoIn int,in fechaIn date, in EstadoIn varchar(40),in precio_tipoIn int ,in acceso_tipoIn int,in operacion_tipo int)
begin
	if operacion_tipo = 0
		then select id_evento, nombre from evento,espectaculo where  evento.id_recinto=id_recintoIn and evento.fecha=fechaIn and evento.estado=EstadoIn and espectaculo.id_espectaculo=evento.id_espectaculo;
	else
			if operacion_tipo =1 then
					if acceso_tipoIn =1 then
						select evento.id_evento, nombre,P_Jubilado,nombre_grada from evento, espectaculo,define,grada
						where define.id_evento=evento.id_evento and define.P_Jubilado<precio_tipoIn and define.P_Jubilado>0 and espectaculo.id_espectaculo=evento.id_espectaculo and define.id_grada=grada.id_grada ;
						end if;
					if acceso_tipoIn=2 then
					select evento.id_evento, nombre,P_Adulto,nombre_grada from evento, espectaculo,define,grada
					where define.id_evento=evento.id_evento and define.P_Adulto<precio_tipoIn and define.P_Adulto>0 and espectaculo.id_espectaculo=evento.id_espectaculo and define.id_grada=grada.id_grada ;
					end if;
					if acceso_tipoIn=3 then
					select evento.id_evento, nombre,P_Infantil,nombre_grada from evento, espectaculo,define,grada
					where define.id_evento=evento.id_evento and define.P_Infantil<precio_tipoIn and define.P_Infantil>0 and espectaculo.id_espectaculo=evento.id_espectaculo and define.id_grada=grada.id_grada ;
					end if;
					if acceso_tipoIn=4 then
					select evento.id_evento, nombre,P_Parado,nombre_grada from evento, espectaculo,define,grada
					where define.id_evento=evento.id_evento and define.P_Parado<precio_tipoIn and define.P_Parado>0 and espectaculo.id_espectaculo=evento.id_espectaculo and define.id_grada=grada.id_grada ;
					end if;
						end if;

		if operacion_tipo= 2 then
			if acceso_tipoIn =1 then
			select evento.id_evento, nombre,P_Jubilado,nombre_grada from evento, espectaculo,define,grada
			where define.id_evento=evento.id_evento and define.P_Jubilado<precio_tipoIn and define.P_Jubilado>0 and espectaculo.id_espectaculo=evento.id_espectaculo and define.id_grada=grada.id_grada and evento.id_recinto=id_recintoIn and evento.fecha=fechaIn and evento.estado=EstadoIn;
			end if;
				if acceso_tipoIn =2 then
				select evento.id_evento, nombre,P_Adulto,nombre_grada from evento, espectaculo,define,grada
				where define.id_evento=evento.id_evento and define.P_Adulto<precio_tipoIn and define.P_Adulto>0 and espectaculo.id_espectaculo=evento.id_espectaculo and define.id_grada=grada.id_grada and evento.id_recinto=id_recintoIn and evento.fecha=fechaIn and evento.estado=EstadoIn;
				end if;
				if acceso_tipoIn =3 then
				select evento.id_evento, nombre,P_Infantil,nombre_grada from evento, espectaculo,define,grada
				where define.id_evento=evento.id_evento and define.P_Infantil<precio_tipoIn and define.P_Infantil>0 and espectaculo.id_espectaculo=evento.id_espectaculo and define.id_grada=grada.id_grada and evento.id_recinto=id_recintoIn and evento.fecha=fechaIn and evento.estado=EstadoIn;
				end if;
				if acceso_tipoIn =4 then
				select evento.id_evento, nombre,P_Parado,nombre_grada from evento, espectaculo,define,grada
				where define.id_evento=evento.id_evento and define.P_Parado<precio_tipoIn and define.P_Parado>0 and espectaculo.id_espectaculo=evento.id_espectaculo and define.id_grada=grada.id_grada and evento.id_recinto=id_recintoIn and evento.fecha=fechaIn and evento.estado=EstadoIn;
				end if;
				end if;


end if;
end//



drop procedure if exists consultar_precio_grada_evento//
create procedure consultar_precio_grada_evento(in id_eventoIn int,in id_gradaIN int, in tipoIn int )
BEGIN
	     if tipoIn =1 then select nombre,P_Jubilado,nombre_grada from espectaculo,define,grada,evento where define.id_evento=id_eventoIn and define.id_grada=id_gradaIn and grada.id_grada=id_gradaIn and evento.id_evento=id_eventoIn and evento.id_espectaculo=espectaculo.id_espectaculo;
			 end if;
				if tipoIn =2 then select nombre,P_Adulto,nombre_grada from espectaculo,define,grada,evento where define.id_evento=id_eventoIn and define.id_grada=id_gradaIn and grada.id_grada=id_gradaIn and evento.id_evento=id_eventoIn and evento.id_espectaculo=espectaculo.id_espectaculo;
				end if;
			 if tipoIn =3 then select nombre,P_Infantil,nombre_grada from espectaculo,define,grada,evento where define.id_evento=id_eventoIn and define.id_grada=id_gradaIn and grada.id_grada=id_gradaIn and evento.id_evento=id_eventoIn and evento.id_espectaculo=espectaculo.id_espectaculo;
			 end if;
			 if tipoIn =4 then select nombre,P_Parado,nombre_grada from espectaculo,define,grada,evento where define.id_evento=id_eventoIn and define.id_grada=id_gradaIn and grada.id_grada=id_gradaIn and evento.id_evento=id_eventoIn and evento.id_espectaculo=espectaculo.id_espectaculo;
			 end if;
			 end//





  drop procedure if exists Pre_Reserva//

create procedure Pre_Reserva(in DNI varchar(50), in id_evento varchar(50),in id_grada varchar(50), in id_localidad varchar(50), in estado varchar(50), in tipo_cliente varchar(50))
BEGIN
declare limite int;
declare tempo_restante int;
declare estado_evento varchar(50);
declare infantil int;
declare adulto int;
declare jubilado int;
declare parado int;
declare my_fecha TIMESTAMP;


/* AQUI REALIZAMOS AS COMPROBACION TEMPORAIS DAS PRE-RESERVAS*/

select fecha into my_fecha from evento where evento.id_evento= id_evento;
select t2 into limite from evento where evento.id_evento = id_evento;
select evento.estado into estado_evento from evento where evento.id_evento=id_evento;
set tempo_restante = TIMESTAMPDIFF(MINUTE, CURRENT_TIMESTAMP(), my_fecha);
if (limite > tempo_restante) then
     if(tempo_restante < 0) then
         SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "O EVENTO XA COMEZOU, NON SE PERMITEN RESERVAS";
     else
     if((STRCMP(estado,'pre-reservado'))=0) then
         SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'XA NON SE ADMITEN PRE-RESERVAS [T2]';
     end if;
     end if;
 end if;
if (STRCMP(estado_evento,'abierto') != 0) then
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'O EVENTO INDICADO NON ESTA DISPOÑIBLE';
end if;
/* Comprobamos se a entrada e a adecuada */
select Entradas_infantil, Entradas_parado, Entradas_adulto, Entradas_jubilado into infantil, parado, adulto, jubilado from evento where
    evento.id_evento=id_evento;
if (STRCMP(tipo_cliente,'infantil') = 0 && infantil <= 0) then
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'O EVENTO INDICADO NON ESTA DISPOÑIBLE PARA ESTE TIPO DE USUARIO';
end if;
if (STRCMP(tipo_cliente,'parado') = 0 && parado <= 0) then
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'O EVENTO INDICADO NON ESTA DISPOÑIBLE PARA ESTE TIPO DE USUARIO';
end if;
if (STRCMP(tipo_cliente,'jubilado') = 0 && jubilado <= 0) then
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'O EVENTO INDICADO NON ESTA DISPOÑIBLE PARA ESTE TIPO DE USUARIO';
end if;
if (STRCMP(tipo_cliente,'adulto') = 0 && adulto <= 0) then
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'O EVENTO INDICADO NON ESTA DISPOÑIBLE PARA ESTE TIPO DE USUARIO';
end if;
    
/* AQUI RELAIZAMOS AS COMPROBACIONS DE ESTADO*/
IF NOT EXISTS (select localidad.id_localidad from localidad where localidad.id_localidad not in (select reserva.id_localidad from reserva where reserva.id_evento=id_evento) 
and localidad.id_grada=id_grada and localidad.id_localidad=id_localidad and localidad.estado='libre') then
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "LOCALIDADE SELECCIONA NON DISPOÑIBLE";
else
    insert into reserva values (null, DNI, id_evento, id_grada, id_localidad, NOW(), estado);
    select 'Operación realizada con éxito';
end if;
END//





drop procedure if
 exists Listar_Localidades//
create procedure Listar_Localidades (in id_evento int, in id_gradaIn int)
BEGIN
declare recinto int;
declare grada int;
select evento.id_recinto into recinto from evento where evento.id_evento=id_evento;
select grada.id_grada into grada from grada where grada.id_recinto=recinto and grada.id_grada=id_gradaIn;

select localidad.id_localidad from localidad where localidad.id_localidad not in (select reserva.id_localidad from reserva where reserva.id_evento=id_evento) 
    and localidad.id_recinto=recinto and localidad.id_grada=grada and localidad.estado='libre';

END//




drop procedure if exists Anulacion //
create procedure Anulacion (in id_reserva varchar(50), in DNI varchar(50))
BEGIN 	
declare limite int;
declare tempo_restante int;
declare grada int;
declare id_evento int;
declare id_espectaculo int;
declare id_recinto int;
declare id_localidad int;
declare my_fecha TIMESTAMP;
select reserva.id_evento into id_evento from reserva where reserva.id_reserva = id_reserva and reserva.DNI=DNI;
select reserva.id_localidad into id_localidad from reserva where reserva.id_reserva = id_reserva and reserva.DNI=DNI;
select evento.id_espectaculo into id_espectaculo from evento where evento.id_evento = id_evento;
select evento.id_recinto into id_recinto from evento where evento.id_evento = id_evento ;
select evento.fecha into my_fecha from evento where evento.id_evento = id_evento;
select t3 into limite from evento where evento.id_evento = id_evento and evento.id_espectaculo=id_espectaculo and evento.id_recinto =id_recinto and evento.fecha=fecha;
set tempo_restante = TIMESTAMPDIFF(MINUTE, CURRENT_TIMESTAMP(), my_fecha);


IF NOT EXISTS (select reserva.id_localidad from reserva where reserva.id_reserva=id_reserva and reserva.id_evento=id_evento and reserva.id_localidad=id_localidad) then
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "LOCALIDADE NON RESERVADA OU PRE-RESERVADA";
end if;

if (limite > tempo_restante) then
    if(tempo_restante < 0) then
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "O EVENTO XA COMEZOU, NON SE PERMITEN ANULACIONS";
    else
        
        delete from reserva where reserva.id_reserva=id_reserva and reserva.DNI=DNI and reserva.id_evento=id_evento  and reserva.id_localidad=id_localidad;
        select 'Anulado con penalizacion';
    end if;
else

delete from reserva where reserva.id_reserva=id_reserva and reserva.DNI=DNI and reserva.id_evento=id_evento  and reserva.id_localidad=id_localidad;
select 'Anulado correctamente';
end if;
END//




drop procedure if exists Confirmar_Reserva//
create procedure Confirmar_Reserva (in id_reserva varchar(50), in DNI varchar(50))
BEGIN
declare my_t1 int;
declare tempo_prereservado int;
declare FechaRealizacion datetime;
declare my_id_evento int;

select id_evento into my_id_evento from reserva where id_reserva=id_reserva;
IF NOT EXISTS (select reserva.id_reserva from reserva where reserva.id_reserva=id_reserva and reserva.estado='pre-reservado') then
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "ESTA PRE-RESERVA NON EXISTE OU XA ESTA CONFIRMADA";
end if;

SELECT t1 into my_t1 from evento where evento.id_evento=my_id_evento;
SELECT reserva.FechaRealizacion into FechaRealizacion from reserva where reserva.id_reserva=id_reserva;

set tempo_prereservado = TIMESTAMPDIFF(MINUTE, FechaRealizacion, CURRENT_TIMESTAMP());
    if(my_t1 < tempo_prereservado) then
        delete from reserva where reserva.id_reserva = id_reserva;
        SELECT "ESTA PRE-RESERVA ESTA CADUCADA";
    END IF;

update reserva set estado='reservado' where id_reserva=id_reserva;
END//

