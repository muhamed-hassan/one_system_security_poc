
CREATE SCHEMA `system_security`;

USE `system_security`;

/* ********************************************************************************************************* */
/* ********************************************************************************************************* */

CREATE TABLE `system_security_configuration` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `jwt_secret` VARCHAR(250) NOT NULL,
  `jwt_expiration` INT UNSIGNED NOT NULL,
  `authentication_path` VARCHAR(100) NOT NULL,
  `automated_system_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`)
);

/* ********************************************************************************************************* */

CREATE TABLE `system_actor` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_UQ` (`type`)
);

/* ********************************************************************************************************* */

CREATE TABLE `screen` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UQ` (`name`)
);

/* ********************************************************************************************************* */

CREATE TABLE `user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `mobile` CHAR(11) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `account_non_expired` bit(1) NOT NULL DEFAULT b'0',
  `account_non_locked` bit(1) NOT NULL DEFAULT b'0',
  `credentials_non_expired` bit(1) NOT NULL DEFAULT b'0',
  `enabled` bit(1) NOT NULL DEFAULT b'0',
  `system_actor_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UQ` (`username`),
  UNIQUE KEY `mobile_UQ` (`mobile`),
  UNIQUE KEY `email_UQ` (`email`),
  FOREIGN KEY (`system_actor_id`) REFERENCES `system_actor` (`id`)
);

/* ********************************************************************************************************* */

CREATE TABLE `granted_authority` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `system_actor_id` INT UNSIGNED NOT NULL,
  `screen_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`system_actor_id`) REFERENCES `system_actor` (`id`),
  FOREIGN KEY (`screen_id`) REFERENCES `screen` (`id`)
);
