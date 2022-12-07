-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: system_security
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `granted_authority`
--

DROP TABLE IF EXISTS `granted_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `granted_authority` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `system_actor_id` int unsigned NOT NULL,
  `ui_screen_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `system_actor_id_fk_idx` (`system_actor_id`),
  KEY `ui_screen_id_ga_fk_idx` (`ui_screen_id`),
  CONSTRAINT `system_actor_id_ga_fk` FOREIGN KEY (`system_actor_id`) REFERENCES `system_actor` (`id`),
  CONSTRAINT `ui_screen_id_ga_fk` FOREIGN KEY (`ui_screen_id`) REFERENCES `ui_screen` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `granted_authority`
--

LOCK TABLES `granted_authority` WRITE;
/*!40000 ALTER TABLE `granted_authority` DISABLE KEYS */;
INSERT INTO `granted_authority` VALUES (1,1,1),(2,1,2),(3,1,3),(4,2,4),(5,2,5),(6,2,6);
/*!40000 ALTER TABLE `granted_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `screen_type`
--

DROP TABLE IF EXISTS `screen_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `screen_type` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(25) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_UNIQUE` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `screen_type`
--

LOCK TABLES `screen_type` WRITE;
/*!40000 ALTER TABLE `screen_type` DISABLE KEYS */;
INSERT INTO `screen_type` VALUES (2,'MOBILE'),(1,'WEB');
/*!40000 ALTER TABLE `screen_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_actor`
--

DROP TABLE IF EXISTS `system_actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_actor` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_UNIQUE` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_actor`
--

LOCK TABLES `system_actor` WRITE;
/*!40000 ALTER TABLE `system_actor` DISABLE KEYS */;
INSERT INTO `system_actor` VALUES (1,'user_a'),(2,'user_b');
/*!40000 ALTER TABLE `system_actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_security_configuration`
--

DROP TABLE IF EXISTS `system_security_configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_security_configuration` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `jwt_secret` varchar(250) NOT NULL,
  `jwt_expiration` int unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_security_configuration`
--

LOCK TABLES `system_security_configuration` WRITE;
/*!40000 ALTER TABLE `system_security_configuration` DISABLE KEYS */;
INSERT INTO `system_security_configuration` VALUES (1,'QiiKLh_g0-CmQlx-foyJ0HA_Qnqk5hB5deXTiFCnLzs3NLSwjGzazVOiWEZvB3sCxyQiR2IQJcl5PAqfxzzF-69E18tozdtDXVKChEqT4gTIv66WGKGrfR_anOayiKoZ139CZ_RL0mv9bYYE18J0EdttknsNTR9s7ONc4DNs8Nc',86400000);
/*!40000 ALTER TABLE `system_security_configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ui_screen`
--

DROP TABLE IF EXISTS `ui_screen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ui_screen` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `screen_name` varchar(100) NOT NULL,
  `screen_type_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `screen_type_id_us_fk_idx` (`screen_type_id`),
  CONSTRAINT `screen_type_id_us_fk` FOREIGN KEY (`screen_type_id`) REFERENCES `screen_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ui_screen`
--

LOCK TABLES `ui_screen` WRITE;
/*!40000 ALTER TABLE `ui_screen` DISABLE KEYS */;
INSERT INTO `ui_screen` VALUES (1,'screen_name_x1',1),(2,'screen_name_x2',1),(3,'screen_name_x3',1),(4,'screen_name_x4',2),(5,'screen_name_x5',2),(6,'screen_name_x6',2);
/*!40000 ALTER TABLE `ui_screen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
  UNIQUE KEY `user_name_UNIQUE` (`username`),
  UNIQUE KEY `mobile_UNIQUE` (`mobile`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `system_actor_id_u_fk_idx` (`system_actor_id`),
  CONSTRAINT `system_actor_id_u_fk` FOREIGN KEY (`system_actor_id`) REFERENCES `system_actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-01 12:22:29
