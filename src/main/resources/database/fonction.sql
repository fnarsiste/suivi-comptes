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