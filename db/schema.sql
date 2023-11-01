
-- Run using MySQL command
CREATE SCHEMA `system_security`;

USE `system_security`;

/* ********************************************************************************************************* */
/* ********************************************************************************************************* */

CREATE TABLE `screen_type` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(25) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `screen_type_type_UQ` (`type`)
);

/* ********************************************************************************************************* */

CREATE TABLE `system_actor` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `system_actor_type_UQ` (`type`)
);

/* ********************************************************************************************************* */

CREATE TABLE `system_security_configuration` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `jwt_secret` varchar(250) NOT NULL,
  `jwt_expiration` int unsigned NOT NULL,
  `authentication_path` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
);

/* ********************************************************************************************************* */

CREATE TABLE `ui_screen` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `screen_name` varchar(100) NOT NULL,
  `screen_type_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`screen_type_id`) REFERENCES `screen_type` (`id`)
);

/* ********************************************************************************************************* */

CREATE TABLE `user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `mobile` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `account_non_expired` bit(1) NOT NULL DEFAULT b'0',
  `account_non_locked` bit(1) NOT NULL DEFAULT b'0',
  `credentials_non_expired` bit(1) NOT NULL DEFAULT b'0',
  `enabled` bit(1) NOT NULL DEFAULT b'0',
  `system_actor_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_user_name_UQ` (`username`),
  UNIQUE KEY `user_mobile_UQ` (`mobile`),
  UNIQUE KEY `user_email_UQ` (`email`),
  FOREIGN KEY (`system_actor_id`) REFERENCES `system_actor` (`id`)
);

/* ********************************************************************************************************* */

CREATE TABLE `granted_authority` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `system_actor_id` int unsigned NOT NULL,
  `ui_screen_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`system_actor_id`) REFERENCES `system_actor` (`id`),
  FOREIGN KEY (`ui_screen_id`) REFERENCES `ui_screen` (`id`)
);
