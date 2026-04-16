-- INSERT INTO authors(firstname,lastname,email)
-- value("Mario","Rossi","mariorossi@test.it");

-- INSERT INTO authors(firstname,lastname,email)
-- value("Giuseppe","Verdi","giuseppeverdi@test.it");


-- INSERT INTO posts(title,body,publish_date,author_id)
-- SELECT 'Lorem ipsum....','Ciao sono Mario', '2020-01-01',id
-- FROM authors
-- WHERE firstname = 'Mario'
-- and lastname = 'Rossi';

-- INSERT INTO posts(title,body,publish_date,author_id)
-- SELECT 'Lorem ipsum....','Amo la pizza', '2020-01-01' ,id
-- FROM authors
-- WHERE firstname = 'Mario'
-- and lastname = 'Rossi';


INSERT INTO comments(email,body,date,post_id)
value("mariorossi@test.it","Ciao sono Mario","2020-01-01",1),
("giuseppeverdi@test.it","Ciao sono Giuseppe","2020-01-01",2);
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE comments;
-- TRUNCATE TABLE posts;
-- TRUNCATE TABLE authors;
-- SET FOREIGN_KEY_CHECKS = 1;