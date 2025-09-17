USE testdb;

INSERT INTO location (description,id,latitude,longitude,name,time_zone,type) 
VALUES ('default','a62102c7-c103-4546-90ce-91cff7395894',40.422057,-3.702582,'Madrid','Europe/Madrid','residence')
ON DUPLICATE KEY UPDATE latitude =40.422057,longitude=-3.702582,time_zone='Europe/Madrid';

INSERT INTO location (description,id,latitude,longitude,name,time_zone,type)
VALUES ('default','a62102c7-c103-4546-90ce-91cff7395895',51.50801699358535, -0.14211791940492488,'London','Europe/Madrid','residence')
ON DUPLICATE KEY UPDATE latitude =51.50801699358535,longitude=-0.14211791940492488,time_zone='Europe/London';

INSERT INTO location (description,id,latitude,longitude,name,time_zone,type)
VALUES ('default','a62102c7-c103-4546-90ce-91cff7395896',48.85692756477395, 2.3494368788655042,'Paris','Europe/Paris','residence')
ON DUPLICATE KEY UPDATE latitude =48.85692756477395,longitude=2.3494368788655042,time_zone='Europe/Paris';

INSERT INTO location (description,id,latitude,longitude,name,time_zone,type)
VALUES ('default','a62102c7-c103-4546-90ce-91cff7395897',52.51813528009776, 13.4027152485495,'Berlin','Europe/Berlin','residence')
ON DUPLICATE KEY UPDATE latitude =52.51813528009776,longitude=13.4027152485495,time_zone='Europe/Berlin';

INSERT INTO location (description,id,latitude,longitude,name,time_zone,type)
VALUES ('default','a62102c7-c103-4546-90ce-91cff73958948',41.89530385638691, 12.492904196909688,'Rome','Europe/Rome','residence')
ON DUPLICATE KEY UPDATE latitude =41.89530385638691 ,longitude=12.492904196909688,time_zone='Europe/Rome';

INSERT INTO location (description,id,latitude,longitude,name,time_zone,type)
VALUES ('default','a62102c7-c103-4546-90ce-91cff7395899',31.249977600881536, 121.49646926813142,'Shanghai','Asia/Shanghai','residence')
ON DUPLICATE KEY UPDATE latitude =431.249977600881536, longitude=121.49646926813142, time_zone='Asia/Shanghai';

INSERT INTO location (description,id,latitude,longitude,name,time_zone,type)
VALUES ('default','a62102c7-c103-4546-90ce-91cff7395901',40.710037886739386, -73.99736741025282,'New York','America/New_York','residence')
ON DUPLICATE KEY UPDATE latitude =440.710037886739386,longitude=-73.99736741025282,time_zone='America/New_York';

INSERT INTO location (description,id,latitude,longitude,name,time_zone,type)
VALUES ('default','a62102c7-c103-4546-90ce-91cff7395902',34.05593258909927, -118.26485115515537,'Los Angeles','America/Los_Angeles','residence')
ON DUPLICATE KEY UPDATE latitude =34.05593258909927, longitude=-118.26485115515537,time_zone='America/Los_Angeles';

/*INSERT INTO tag (id,type,cn,en,es,de,fr,it,pt)
VALUES ( '3c6e4bfa-7019-4257-8ae9-5bad050d9510','service','.net','.net','.net','.net','.net','.net','.net');*/