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
	
-- fonction d'appel du trigger
CREATE OR REPLACE FUNCTION banques_create_trigger_func()
RETURNS trigger
language plpgsql 
AS $$
BEGIN
	IF NEW.CODE IS NULL OR CONCAT('', NEW.CODE) = '' then
		NEW.CODE := create_code(new.id, 'BQ');
	END IF;
	IF NEW.date_creation IS NULL then
		NEW.date_creation = now();
	END IF;
	if new.date_cessation is null then 
		new.date_cessation := 'Infinity'; 
	end if;
	IF NEW.modifier_par IS NULL OR NEW.modifier_par = '' then
		NEW.modifier_par = 'App';
	END IF;
	RETURN NEW;
END;
$$;	

-- trigger d'insertion dans banque
CREATE OR REPLACE TRIGGER TRG_INSERT_BANQUE 
BEFORE INSERT ON banques 
FOR EACH ROW EXECUTE PROCEDURE banques_create_trigger_func();


-- ///////
-- fonction d'appel du trigger
CREATE OR REPLACE FUNCTION fonctions_create_trigger_func()
RETURNS trigger
language plpgsql 
AS $$
BEGIN
	IF NEW.CODE IS NULL OR CONCAT('', NEW.CODE) = '' then
		NEW.CODE := create_code(new.id, 'FN');
	END IF;
	IF NEW.date_creation IS NULL then
		NEW.date_creation = now();
	END IF;
	if new.date_cessation is null then 
		new.date_cessation := 'Infinity'; 
	end if;
	IF NEW.modifier_par IS NULL OR NEW.modifier_par = '' then
		NEW.modifier_par = 'App';
	END IF;
	RETURN NEW;
END;
$$;	

CREATE OR REPLACE TRIGGER TRG_INSERT_FONCTIONS
BEFORE INSERT ON fonctions 
FOR EACH ROW EXECUTE PROCEDURE fonctions_create_trigger_func();
-- ///////


-- ///////
-- fonction d'appel du trigger
CREATE OR REPLACE FUNCTION agents_create_trigger_func()
RETURNS trigger
language plpgsql 
AS $$
BEGIN
	IF NEW.CODE IS NULL OR CONCAT('', NEW.CODE) = '' then
		NEW.CODE := create_code(new.id, 'AGT', 16);
	END IF;
	IF NEW.date_creation IS NULL then
		NEW.date_creation = now();
	END IF;
	if new.date_cessation is null then 
		new.date_cessation := 'Infinity'; 
	end if;
	IF NEW.modifier_par IS NULL OR NEW.modifier_par = '' then
		NEW.modifier_par = 'App';
	END IF;
	RETURN NEW;
END;
$$;	

CREATE OR REPLACE TRIGGER TRG_INSERT_AGENTS
BEFORE INSERT ON agents 
FOR EACH ROW EXECUTE PROCEDURE agents_create_trigger_func();

-- ///////

-- Auto-generated SQL script #202302151750
INSERT INTO banques (code,date_cessation,date_creation,modifier_par,libelle,adresse,sigle)
	VALUES ('BQ-0000000000004', now(),'2023-02-15 17:45:58.570','N/A','BANK OF AFRICA','','BOA');


