DROP TABLE people IF EXISTS;
CREATE TABLE `people` (
  `person_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE USER_BATCH IF EXISTS;
CREATE TABLE `USER_BATCH` (
  `userid` bigint(20) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `emailid` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;