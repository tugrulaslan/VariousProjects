HibernateExample
================

HibernateExample
This simple hibernate example carries out CRUD objectives. You will find the db structure below
CREATE TABLE userdb.Employee (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `lastname` varchar(20) DEFAULT NULL,
  `reg_date` timestamp DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
