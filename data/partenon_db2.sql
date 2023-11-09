CREATE DATABASE  IF NOT EXISTS `partenon_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `partenon_db`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: partenon_db
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `expositions`
--

DROP TABLE IF EXISTS `expositions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expositions` (
  `id` varchar(50) NOT NULL,
  `museum_id` varchar(50) NOT NULL,
  `photo` varchar(200) NOT NULL,
  `name` varchar(30) NOT NULL,
  `description` varchar(255) NOT NULL DEFAULT 'Sin informacion',
  PRIMARY KEY (`id`),
  KEY `museum_exposition_fk_idx` (`museum_id`),
  CONSTRAINT `museum_exposition_fk` FOREIGN KEY (`museum_id`) REFERENCES `museum` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expositions`
--

LOCK TABLES `expositions` WRITE;
/*!40000 ALTER TABLE `expositions` DISABLE KEYS */;
/*!40000 ALTER TABLE `expositions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `museum`
--

DROP TABLE IF EXISTS `museum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `museum` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `province` varchar(60) NOT NULL,
  `city` varchar(60) NOT NULL,
  `street` varchar(60) NOT NULL,
  `address_number` varchar(5) NOT NULL DEFAULT 'S/N',
  `description` varchar(254) NOT NULL DEFAULT 'Sin informacion',
  `user_id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_museum_fk_idx` (`user_id`),
  CONSTRAINT `user_museum_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `museum`
--

LOCK TABLES `museum` WRITE;
/*!40000 ALTER TABLE `museum` DISABLE KEYS */;
/*!40000 ALTER TABLE `museum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `museum_banner`
--

DROP TABLE IF EXISTS `museum_banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `museum_banner` (
  `museum_id` varchar(50) NOT NULL,
  `banner` mediumtext NOT NULL,
  PRIMARY KEY (`museum_id`),
  CONSTRAINT `museum__banner_fk` FOREIGN KEY (`museum_id`) REFERENCES `museum` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `museum_banner`
--

LOCK TABLES `museum_banner` WRITE;
/*!40000 ALTER TABLE `museum_banner` DISABLE KEYS */;
/*!40000 ALTER TABLE `museum_banner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `museum_contact`
--

DROP TABLE IF EXISTS `museum_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `museum_contact` (
  `museum_id` varchar(50) NOT NULL,
  `type` enum('TWITTER','FACEBOOK','WSP','EMAIL','INSTAGRAM') NOT NULL,
  `contact` varchar(128) NOT NULL,
  PRIMARY KEY (`museum_id`,`type`),
  KEY `museum_contact_fk_idx` (`museum_id`),
  CONSTRAINT `museum_contact_fk` FOREIGN KEY (`museum_id`) REFERENCES `museum` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



CREATE TABLE `museum_contact` (
  `museum_id` varchar(50) NOT NULL,
  `type` enum('TWITTER','FACEBOOK','WSP','EMAIL','INSTAGRAM') NOT NULL,
  `contact` varchar(128) NOT NULL,
  PRIMARY KEY (`museum_id`,`type`),
  KEY `museum_contact_fk_idx` (`museum_id`),
  CONSTRAINT `museum_contact_fk` FOREIGN KEY (`museum_id`) REFERENCES `museum` (`id`)
);

--
-- Dumping data for table `museum_contact`
--

LOCK TABLES `museum_contact` WRITE;
/*!40000 ALTER TABLE `museum_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `museum_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `museum_plan`
--

DROP TABLE IF EXISTS `museum_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `museum_plan` (
  `museum_id` varchar(50) NOT NULL,
  `building_plan` varchar(200) NOT NULL,
  PRIMARY KEY (`museum_id`),
  UNIQUE KEY `museum_id_UNIQUE` (`museum_id`),
  CONSTRAINT `museum_plan_fk` FOREIGN KEY (`museum_id`) REFERENCES `museum` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `museum_plan`
--

LOCK TABLES `museum_plan` WRITE;
/*!40000 ALTER TABLE `museum_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `museum_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `museum_id` int unsigned NOT NULL,
  `day` enum('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY') NOT NULL,
  `shift` enum('MORNING','AFTERNOON','CONTINUOUS') NOT NULL,
  `opening` varchar(4) NOT NULL,
  `close` varchar(4) NOT NULL,
  PRIMARY KEY (`museum_id`,`day`,`shift`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



CREATE TABLE `schedule` (
  `museum_id` int unsigned NOT NULL,
  `day` enum('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY') NOT NULL,
  `shift` enum('MORNING','AFTERNOON','CONTINUOUS') NOT NULL,
  `opening` varchar(4) NOT NULL,
  `close` varchar(4) NOT NULL,
  PRIMARY KEY (`museum_id`,`day`,`shift`)
);

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL,
  `email` varchar(128) NOT NULL,
  `name` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `role` enum('USER','ADMIN') NOT NULL,
  `password` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
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

-- Dump completed on 2023-11-08  2:07:35
