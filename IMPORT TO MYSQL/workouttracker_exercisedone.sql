-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: workouttracker
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `exercisedone`
--

DROP TABLE IF EXISTS `exercisedone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `exercisedone` (
  `idexercise_done` int(11) NOT NULL AUTO_INCREMENT,
  `exerciseid` int(11) DEFAULT NULL,
  `reps` int(3) DEFAULT NULL,
  `sets` int(3) DEFAULT NULL,
  `workoutid` int(11) DEFAULT NULL,
  `weight_lbs` double DEFAULT NULL,
  PRIMARY KEY (`idexercise_done`),
  KEY `workoutid_idx` (`workoutid`) /*!80000 INVISIBLE */,
  KEY `e_name_idx` (`exerciseid`),
  CONSTRAINT `e_name` FOREIGN KEY (`exerciseid`) REFERENCES `exercises` (`idexercises`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercisedone`
--

LOCK TABLES `exercisedone` WRITE;
/*!40000 ALTER TABLE `exercisedone` DISABLE KEYS */;
INSERT INTO `exercisedone` VALUES (3,1,1,1,175,0),(4,5,10,3,176,35),(5,8,10,3,176,50),(6,7,10,3,176,30),(7,9,20,3,176,60),(8,16,20,3,176,60),(9,24,20,5,176,0),(10,1,1,20,177,0),(11,1,1,20,177,0),(12,1,1,20,177,0),(13,1,1,20,177,0),(14,1,1,20,177,0),(15,1,1,20,177,0),(16,1,1,20,177,0),(17,1,1,20,177,0),(18,1,1,20,177,0),(19,1,1,20,177,0),(20,1,1,20,177,0),(21,1,1,20,177,0),(22,1,1,20,177,0),(23,1,1,20,177,0),(24,1,1,20,177,0),(25,1,1,20,177,0),(26,1,1,20,177,0),(27,1,1,20,177,0),(28,1,1,20,177,0),(29,3,3,1,179,0),(30,1,1,1,180,0),(31,4,1,3,181,0),(32,7,10,2,181,66),(33,1,1,1,182,0),(34,1,1,1,182,0),(35,1,1,1,182,0);
/*!40000 ALTER TABLE `exercisedone` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-07 21:39:13
