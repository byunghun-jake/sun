CREATE SCHEMA IF NOT EXISTS `tingle` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `tingle` ;
CREATE TABLE IF NOT EXISTS `tingle`.`member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `member_id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `auth` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `member_id_UNIQUE` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `tingle`.`calendar` (
  `calendar_code` varchar(50) NOT NULL,
  `calendar_name` varchar(50) NOT NULL,
  `member_id` varchar(255) NOT NULL,
  PRIMARY KEY (`calendar_code`),
  KEY `calendar_fk_member_id_idx` (`member_id`),
  CONSTRAINT `calendar_fk_member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `tingle`.`calendar_share` (
  `share_id` int NOT NULL AUTO_INCREMENT,
  `calendar_code` varchar(50) NOT NULL,
  `member_id` varchar(255) NOT NULL,
  PRIMARY KEY (`share_id`),
  KEY `calendar_pk_caelndar_code_idx` (`calendar_code`),
  KEY `calendar_share_fk_member_id_idx` (`member_id`),
  CONSTRAINT `calendar_pk_caelndar_code` FOREIGN KEY (`calendar_code`) REFERENCES `calendar` (`calendar_code`),
  CONSTRAINT `calendar_share_fk_member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `tingle`.`mission` (
  `mission_id` int NOT NULL AUTO_INCREMENT,
  `mission_name` varchar(45) NOT NULL,
  `start_date` varchar(50) NOT NULL,
  `end_date` varchar(50) NOT NULL,
  `tag` varchar(100) NOT NULL,
  `calendar_code` varchar(50) NOT NULL,
  `end_time` varchar(255) DEFAULT NULL,
  `start_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mission_id`),
  KEY `calendar_pk_calendar_code_idx` (`calendar_code`),
  CONSTRAINT `calendar_pk_calendar_code` FOREIGN KEY (`calendar_code`) REFERENCES `calendar` (`calendar_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `tingle`.`mission_file` (
  `file_id` int NOT NULL AUTO_INCREMENT,
  `file_uuid` varchar(255) NOT NULL,
  `mission_id` int NOT NULL,
  PRIMARY KEY (`file_id`),
  UNIQUE KEY `mission_id_UNIQUE` (`mission_id`),
  CONSTRAINT `mission_file_fk_mission_id` FOREIGN KEY (`mission_id`) REFERENCES `mission` (`mission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 



