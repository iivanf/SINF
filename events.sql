use proyecto25;
drop event if exists Evento_Comprobar_Pre_reserva;
drop event if exists Evento_Estado_Evento;

delimiter //

create event Evento_Comprobar_Pre_reserva
on schedule EVERY 1 MINUTE STARTS current_timestamp()
DO
BEGIN
declare my_id_reserva varchar(50);
declare my_id_evento int;
declare my_FechaRealizacion datetime;

DECLARE tiempo_pre_reservado int;
DECLARE done INT DEFAULT 0;
DECLARE my_t1 INT;
DECLARE reservas CURSOR FOR SELECT id_reserva, id_evento, FechaRealizacion FROM reserva where reserva.estado='pre-reservado';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
OPEN reservas;
    read_loop: LOOP
        FETCH reservas INTO my_id_reserva, my_id_evento, my_FechaRealizacion;
        select t1 into my_t1 from evento where id_evento=my_id_evento;
        IF done = 1 THEN
            LEAVE read_loop;
        END IF;
        set tiempo_pre_reservado = TIMESTAMPDIFF(MINUTE, my_FechaRealizacion, CURRENT_TIMESTAMP());
        if(my_t1 < tiempo_pre_reservado) then
            delete from reserva where reserva.id_reserva=my_id_reserva;
        END IF;
    END LOOP;
CLOSE reservas;
end//



create event Evento_Estado_Evento
on schedule EVERY 1 MINUTE STARTS current_timestamp()
do
begin
declare my_id_espectaculo varchar(50);
declare my_id_recinto int;
declare my_fecha datetime;
DECLARE done INT DEFAULT FALSE;
DECLARE cur1 CURSOR FOR SELECT id_espectaculo, id_recinto, fecha FROM evento;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
OPEN cur1;
    read_loop: LOOP
        FETCH cur1 INTO my_id_espectaculo, my_id_recinto, my_fecha;
        IF done THEN
            LEAVE read_loop;
        END IF;
        if(my_fecha < current_timestamp()) then update evento set estado='cerrado' where id_espectaculo=my_id_espectaculo and id_recinto=my_id_recinto and fecha=my_fecha;
        end if;
        if(TIMESTAMPDIFF(HOUR, my_fecha, current_timestamp()) > 24) then update evento set estado='finalizado' where id_espectaculo=my_id_espectaculo and id_recinto=my_id_recinto and fecha=my_fecha;
        end if;
    END LOOP;
close cur1;
end//


delimiter ;
