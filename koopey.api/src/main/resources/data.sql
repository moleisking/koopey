USE testdb; 

INSERT INTO location (description,id,latitude,longitude,name,time_zone,type) 
VALUES ('default',UUID_TO_BIN('a62102c7-c103-4546-90ce-91cff7395894'),40.422057,-3.702582,'Madrid','Europe/Madrid','residence')
ON DUPLICATE KEY UPDATE latitude =40.422057,longitude=-3.702582,time_zone='Europe/Madrid'