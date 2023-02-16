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