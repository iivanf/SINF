delimiter //
drop trigger if exists precios_eventos;

CREATE TRIGGER precios_eventos before INSERT ON define for each row
begin
      declare jub boolean;
      declare inf boolean;
      declare adu boolean;
      declare par boolean;
      select evento.Entradas_infantil,evento.Entradas_jubilado,evento.Entradas_adulto,evento.Entradas_parado into inf,jub,adu,par from evento where evento.id_evento=new.id_evento;
      if jub=0 then set new.P_Jubilado=-1;
      END IF;
      if inf=0 then set new.P_Infantil=-1;
      END IF;
      if adu=0 then set new.P_Adulto=-1;
      END IF;
      if par=0 then set new.P_Parado=-1;
      END IF;
      end//

drop trigger if exists trigger_estado_evento//



create trigger trigger_estado_evento after insert on reserva
for each row
begin
declare my_id_grada int;
DECLARE done INT DEFAULT FALSE;
DECLARE cur1 CURSOR FOR SELECT id_grada FROM define where id_evento=new.id_evento;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
OPEN cur1;
    read_loop: LOOP
    FETCH cur1 INTO my_id_grada;
    IF done THEN
        LEAVE read_loop;
    END IF;
    IF NOT EXISTS (select * from localidad where localidad.id_localidad not in (select reserva.id_localidad from reserva where reserva.id_evento=new.id_evento) 
        and localidad.id_grada=my_id_grada and localidad.estado='libre')then
            update evento set estado='cerrado' where id_evento=new.id_evento;
    else update evento set estado='abierto' where id_evento=new.id_evento;
    end if;
    END LOOP;
close cur1;
end//

delimiter ;
