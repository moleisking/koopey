USE testdb; 

INSERT INTO user (id, alias, name, password)
SELECT "1", "mole" ,'Scott Johnston', "123" AS VALUE
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id ="1");