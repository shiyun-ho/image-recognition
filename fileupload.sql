CREATE database imagerecognition; 

use imagerecognition; 

CREATE table posts(
	id INT NOT NULL AUTO_INCREMENT, 
    blobc MEDIUMBLOB NOT NULL, 
    title VARCHAR(100) NOT NULL, 
    complain VARCHAR(100) NOT NULL, 
    PRIMARY KEY(id)
)

INSERT into posts (blobc, title, complain) VALUES 