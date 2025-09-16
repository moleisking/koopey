USE testdb;

INSERT INTO location (description,id,latitude,longitude,name,time_zone,type) 
VALUES ('default',UUID_TO_BIN('a62102c7-c103-4546-90ce-91cff7395894'),40.422057,-3.702582,'Madrid','Europe/Madrid','residence')
ON DUPLICATE KEY UPDATE latitude =40.422057,longitude=-3.702582,time_zone='Europe/Madrid'

/*INSERT INTO tag (id,type,cn,en,es,de,fr,it,pt)
VALUES ( '3c6e4bfa-7019-4257-8ae9-5bad050d9510','service','.net','.net','.net','.net','.net','.net','.net');*/