-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema mychat
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mychat
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mychat` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mychat` ;

-- -----------------------------------------------------
-- Table `mychat`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mychat`.`users` (
  `phone_number` VARCHAR(20) NOT NULL,
  `display_name` VARCHAR(50) NULL DEFAULT NULL,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `password_hash` VARCHAR(255) NULL DEFAULT NULL,
  `gender` VARCHAR(10) NULL DEFAULT NULL,
  `country` VARCHAR(50) NULL DEFAULT NULL,
  `date_of_birth` DATE NULL DEFAULT NULL,
  `bio` TEXT NULL DEFAULT NULL,
  `status` ENUM('online', 'offline', 'available', 'busy', 'away') NULL DEFAULT NULL,
  `last_seen` TIMESTAMP NULL DEFAULT NULL,
  `picture` BLOB NULL DEFAULT NULL,
  PRIMARY KEY (`phone_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mychat`.`chatbot_settings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mychat`.`chatbot_settings` (
  `user_id` VARCHAR(20) NOT NULL,
  `is_enabled` TINYINT(1) NULL DEFAULT NULL,
  `chatbot_provider` VARCHAR(50) NULL DEFAULT NULL,
  `chatbot_api_key` VARCHAR(250) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `chatbot_settings_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mychat`.`users` (`phone_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mychat`.`contacts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mychat`.`contacts` (
  `user_id` VARCHAR(20) NOT NULL,
  `contact_phone_number` VARCHAR(20) NOT NULL,
  `is_blocked` TINYINT(1) NULL DEFAULT '0',
  PRIMARY KEY (`user_id`, `contact_phone_number`),
  INDEX `contact_phone_number` (`contact_phone_number` ASC) VISIBLE,
  CONSTRAINT `contacts_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mychat`.`users` (`phone_number`),
  CONSTRAINT `contacts_ibfk_2`
    FOREIGN KEY (`contact_phone_number`)
    REFERENCES `mychat`.`users` (`phone_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mychat`.`file_transfers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mychat`.`file_transfers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sender_phone_number` VARCHAR(20) NULL DEFAULT NULL,
  `recipient_phone_number` VARCHAR(20) NULL DEFAULT NULL,
  `file_path` VARCHAR(255) NULL DEFAULT NULL,
  `file_type` VARCHAR(50) NULL DEFAULT NULL,
  `timestamp` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `sender_phone_number` (`sender_phone_number` ASC) VISIBLE,
  INDEX `recipient_phone_number` (`recipient_phone_number` ASC) VISIBLE,
  CONSTRAINT `file_transfers_ibfk_1`
    FOREIGN KEY (`sender_phone_number`)
    REFERENCES `mychat`.`users` (`phone_number`),
  CONSTRAINT `file_transfers_ibfk_2`
    FOREIGN KEY (`recipient_phone_number`)
    REFERENCES `mychat`.`users` (`phone_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mychat`.`my_groups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mychat`.`my_groups` (
  `group_id` INT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(50) NULL DEFAULT NULL,
  `created_by` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`group_id`),
  INDEX `created_by` (`created_by` ASC) VISIBLE,
  CONSTRAINT `my_groups_ibfk_1`
    FOREIGN KEY (`created_by`)
    REFERENCES `mychat`.`users` (`phone_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mychat`.`group_members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mychat`.`group_members` (
  `user_id` VARCHAR(20) NULL DEFAULT NULL,
  `group_id` INT NULL DEFAULT NULL,
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  INDEX `group_id` (`group_id` ASC) VISIBLE,
  CONSTRAINT `group_members_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mychat`.`users` (`phone_number`),
  CONSTRAINT `group_members_ibfk_2`
    FOREIGN KEY (`group_id`)
    REFERENCES `mychat`.`my_groups` (`group_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mychat`.`invitations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mychat`.`invitations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sender_phone_number` VARCHAR(20) NULL DEFAULT NULL,
  `recipient_phone_number` VARCHAR(20) NULL DEFAULT NULL,
  `status` ENUM('pending', 'accepted', 'rejected') NULL DEFAULT NULL,
  `timestamp` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `sender_phone_number` (`sender_phone_number` ASC) VISIBLE,
  INDEX `recipient_phone_number` (`recipient_phone_number` ASC) VISIBLE,
  CONSTRAINT `invitations_ibfk_1`
    FOREIGN KEY (`sender_phone_number`)
    REFERENCES `mychat`.`users` (`phone_number`),
  CONSTRAINT `invitations_ibfk_2`
    FOREIGN KEY (`recipient_phone_number`)
    REFERENCES `mychat`.`users` (`phone_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mychat`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mychat`.`messages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sender_phone_number` VARCHAR(20) NULL DEFAULT NULL,
  `recipient_phone_number` VARCHAR(20) NULL DEFAULT NULL,
  `message_content` TEXT NULL DEFAULT NULL,
  `timestamp` TIMESTAMP NULL DEFAULT NULL,
  `is_read` TINYINT(1) NULL DEFAULT NULL,
  `group_id` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `sender_phone_number` (`sender_phone_number` ASC) VISIBLE,
  INDEX `recipient_phone_number` (`recipient_phone_number` ASC) VISIBLE,
  CONSTRAINT `messages_ibfk_1`
    FOREIGN KEY (`sender_phone_number`)
    REFERENCES `mychat`.`users` (`phone_number`),
  CONSTRAINT `messages_ibfk_2`
    FOREIGN KEY (`recipient_phone_number`)
    REFERENCES `mychat`.`users` (`phone_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
