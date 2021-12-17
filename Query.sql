CREATE DATABASE `webshop`;
USE `webshop`;

CREATE TABLE `user_account` (
  `account` char(18) NOT NULL,
  `password` char(18) NOT NULL,
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_info` (
  `account` char(18) NOT NULL,
  `username` char(18) NOT NULL,
  `email` tinytext,
  `location` tinytext NOT NULL,
  PRIMARY KEY (`account`),
  CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`account`) REFERENCES `user_account` (`account`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `goods` (
  `id` char(16) NOT NULL,
  `name` char(64) NOT NULL,
  `price` double NOT NULL,
  `describe` text NOT NULL,
  `img_raw` longtext NOT NULL,
  `thumbnail_raw` mediumtext NOT NULL,
  `counter` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `id` text NOT NULL,
  `log` longtext NOT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;