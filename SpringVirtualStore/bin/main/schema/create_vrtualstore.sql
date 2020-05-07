-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: virtualstore
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `business_tbl`
--

DROP TABLE IF EXISTS `business_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_tbl` (
  `business_id` int NOT NULL AUTO_INCREMENT,
  `business_user_id` int NOT NULL DEFAULT '0',
  `business_sales` int DEFAULT '1',
  `business_state` int DEFAULT '1',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updata_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`business_id`,`business_user_id`),
  UNIQUE KEY `business_id_UNIQUE` (`business_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_tbl`
--

LOCK TABLES `business_tbl` WRITE;
/*!40000 ALTER TABLE `business_tbl` DISABLE KEYS */;
INSERT INTO `business_tbl` VALUES (1,1,700,1,'2020-05-03 14:52:15','2020-05-03 14:53:56'),(2,1,1000,1,'2020-05-03 14:55:59','2020-05-05 17:33:44'),(3,0,18000,1,'2020-05-06 11:06:52','2020-05-06 11:06:52');
/*!40000 ALTER TABLE `business_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_tbl`
--

DROP TABLE IF EXISTS `cart_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_tbl` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `cart_user_id` int NOT NULL DEFAULT '0',
  `cart_product_id` int NOT NULL DEFAULT '0',
  `cart_state` int NOT NULL DEFAULT '1',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updata_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`cart_id`),
  UNIQUE KEY `cart_id_UNIQUE` (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_tbl`
--

LOCK TABLES `cart_tbl` WRITE;
/*!40000 ALTER TABLE `cart_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_mst`
--

DROP TABLE IF EXISTS `product_mst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_mst` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(20) NOT NULL DEFAULT 'No name',
  `product_price` int DEFAULT '0',
  `product_stock` int DEFAULT '0',
  `product_state` int DEFAULT '1',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updata_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_id_UNIQUE` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_mst`
--

LOCK TABLES `product_mst` WRITE;
/*!40000 ALTER TABLE `product_mst` DISABLE KEYS */;
INSERT INTO `product_mst` VALUES (0,'SampleProduct_1',3000,1000,1,'2020-04-14 12:24:28','2020-05-04 19:59:45'),(1,'SampleProduct_2',5000,500,1,'2020-04-23 02:38:57','2020-05-04 19:59:45'),(2,'SampleProduct_3',10000,100,1,'2020-05-04 11:24:20','2020-05-04 19:59:45');
/*!40000 ALTER TABLE `product_mst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_mst`
--

DROP TABLE IF EXISTS `user_mst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_mst` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(10) NOT NULL DEFAULT 'No name.',
  `user_password` varchar(255) NOT NULL DEFAULT 'password',
  `user_birthday` date DEFAULT NULL,
  `user_gender` varchar(45) DEFAULT 'unselected',
  `user_state` int DEFAULT '1',
  `user_role` varchar(20) DEFAULT 'ROLE_GENERAL',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updata_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`user_name`),
  UNIQUE KEY `user_num_UNIQUE` (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_mst`
--

LOCK TABLES `user_mst` WRITE;
/*!40000 ALTER TABLE `user_mst` DISABLE KEYS */;
INSERT INTO `user_mst` VALUES (0,'admin','$2a$10$rFXsCbWx8vTzTdJuiuN3lOeTrmECJ76lEE2NEy9JwEvNPbp6AB5nG','1900-01-01','unselected',1,'ROLE_ADMIN','2020-04-14 10:33:11','2020-05-04 19:58:32'),(1,'general','$2a$10$9v5PiKuXlROCgABlNxTJmOpOw7LiSoNoN9Q7OalaClT4lEc7LNdQS','1989-06-03','male',1,'ROLE_GENERAL','2020-04-21 13:22:34','2020-05-04 19:58:32');
/*!40000 ALTER TABLE `user_mst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'virtualstore'
--

--
-- Dumping routines for database 'virtualstore'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-07  9:09:14
