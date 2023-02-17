-- CreateCode funtion
CREATE OR REPLACE FUNCTION create_code(id bigint, prefix character varying)
RETURNS character varying
language plpgsql 
AS $$
declare
	code varchar;
	lengthToPad int;
begin
	code := CASE 
		WHEN id < 1000 then lpad(concat('', id), 4, '0')
		when id >= 1000 then concat('', id)
	END;
	return concat(Upper(prefix), '-', code);
end;
$$;

-- create code function
CREATE OR REPLACE FUNCTION create_code(id bigint, prefix character varying, taille integer)
RETURNS character varying
language plpgsql
AS $$
declare
	code varchar;
	lengthToPad int;
begin
	lengthToPad = taille - length(prefix) - 1;
	code = concat(Upper(prefix), '-', lpad(concat('', id), lengthToPad, '0'));   
	return code;
end;
$$;

-- fonction générique pour la creation des triggers
CREATE OR REPLACE FUNCTION fn_create_trigger_table()
RETURNS trigger
language plpgsql 
AS $$
declare 
	ttable character varying;
	prefix character varying;
	taille integer;
begin
	ttable := TG_ARGV[0];
	prefix := TG_ARGV[1];
	taille := TG_ARGV[2];

	IF NEW.CODE IS NULL OR CONCAT('', NEW.CODE) = '' then
		NEW.CODE := (case when taille = 0 then create_code(new.id, prefix) else create_code(new.id, prefix, taille) end);
	END IF;
	IF NEW.modifier_par IS NULL OR NEW.modifier_par = '' then
		NEW.modifier_par = 'App';
	END IF;
	-- traitement a faire pour les différentes tables
	/*case ttable 
		when 'banques' then 
		else 
	end;*/
	NEW.date_creation = current_timestamp;
	new.date_cessation := 'Infinity'; 
	RETURN NEW;
END;
$$;	

-- fonction trigger de mise a jour de l'ID Agent dans User si un agent se voit édité
CREATE OR REPLACE FUNCTION fn_update_agent_in_user()
RETURNS trigger
language plpgsql 
AS $$
declare 
	updatedId integer := (select a.id 
		from agents a where a.matricule = new.matricule and a.date_cessation <> 'infinity' 
		order by id desc limit 1);
begin 
	update users u set u.agent_id = new.id where u.agent_id = updatedId;
	return new;
end;
$$;	
CREATE OR REPLACE TRIGGER trg_update_user_agentid 
AFTER INSERT ON agents 
FOR EACH ROW EXECUTE PROCEDURE fn_update_agent_in_user();

select a.id from agents a where a.matricule = '86438' and a.date_cessation <> 'infinity' order by id desc limit 1;

-- trigger d'insertion dans banque
CREATE OR REPLACE TRIGGER TRG_INSERT_BANQUE 
BEFORE INSERT ON banques 
FOR EACH ROW EXECUTE PROCEDURE fn_create_trigger_table('banques', 'BQ', 0);

CREATE OR REPLACE TRIGGER TRG_INSERT_FONCTIONS
BEFORE INSERT ON fonctions 
FOR EACH ROW EXECUTE PROCEDURE fn_create_trigger_table('fonctions', 'FN', 0);

CREATE OR REPLACE TRIGGER TRG_INSERT_AGENTS
BEFORE INSERT ON agents 
FOR EACH ROW EXECUTE PROCEDURE fn_create_trigger_table('agents', 'AGT', 16);

CREATE OR REPLACE TRIGGER TRG_INSERT_STATUT
BEFORE INSERT ON statuts
FOR EACH ROW EXECUTE PROCEDURE fn_create_trigger_table('statuts', 'ST', 0);

CREATE OR REPLACE TRIGGER TRG_INSERT_PROFILS
BEFORE INSERT ON profils
FOR EACH ROW EXECUTE PROCEDURE fn_create_trigger_table('profils', 'PF', 0);

CREATE OR REPLACE TRIGGER TRG_INSERT_USERS
BEFORE INSERT ON users
FOR EACH ROW EXECUTE PROCEDURE fn_create_trigger_table('users', 'USR', 16);
