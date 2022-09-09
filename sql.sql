-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: estateadvance
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `assignmentbuilding`
--

DROP TABLE IF EXISTS `assignmentbuilding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assignmentbuilding` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `staffid` bigint NOT NULL,
  `buildingid` bigint NOT NULL,
  `createddate` datetime DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_building` (`staffid`),
  KEY `fk_building_user` (`buildingid`),
  CONSTRAINT `fk_building_user` FOREIGN KEY (`buildingid`) REFERENCES `building` (`id`),
  CONSTRAINT `fk_user_building` FOREIGN KEY (`staffid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignmentbuilding`
--

LOCK TABLES `assignmentbuilding` WRITE;
/*!40000 ALTER TABLE `assignmentbuilding` DISABLE KEYS */;
INSERT INTO `assignmentbuilding` VALUES (100,24,4,NULL,NULL,NULL,NULL),(103,23,1,NULL,NULL,NULL,NULL),(105,24,2,NULL,NULL,NULL,NULL),(128,24,64,NULL,NULL,NULL,NULL),(129,25,77,NULL,NULL,NULL,NULL),(130,28,77,NULL,NULL,NULL,NULL),(131,30,79,NULL,NULL,NULL,NULL),(135,25,65,NULL,NULL,NULL,NULL),(136,25,64,NULL,NULL,NULL,NULL),(137,25,3,NULL,NULL,NULL,NULL),(151,30,80,NULL,NULL,NULL,NULL),(152,23,76,NULL,NULL,NULL,NULL),(153,29,78,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `assignmentbuilding` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assignmentcustomer`
--

DROP TABLE IF EXISTS `assignmentcustomer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assignmentcustomer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `staffid` bigint NOT NULL,
  `customerid` bigint NOT NULL,
  `createddate` datetime DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_customer` (`staffid`),
  KEY `fk_customer_user` (`customerid`),
  CONSTRAINT `fk_customer_user` FOREIGN KEY (`customerid`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_user_customer` FOREIGN KEY (`staffid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignmentcustomer`
--

LOCK TABLES `assignmentcustomer` WRITE;
/*!40000 ALTER TABLE `assignmentcustomer` DISABLE KEYS */;
INSERT INTO `assignmentcustomer` VALUES (40,25,14,NULL,NULL,NULL,NULL),(41,28,25,NULL,NULL,NULL,NULL),(43,23,24,NULL,NULL,NULL,NULL),(44,30,24,NULL,NULL,NULL,NULL),(45,28,26,NULL,NULL,NULL,NULL),(46,31,28,NULL,NULL,NULL,NULL),(48,30,27,NULL,NULL,NULL,NULL),(49,29,15,NULL,NULL,NULL,NULL),(50,24,23,NULL,NULL,NULL,NULL),(51,25,32,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `assignmentcustomer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `building` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `street` varchar(255) DEFAULT NULL,
  `ward` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `structure` varchar(255) DEFAULT NULL,
  `numberofbasement` int DEFAULT NULL,
  `floorarea` int DEFAULT NULL,
  `direction` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `rentprice` int DEFAULT NULL,
  `rentpricedescription` text,
  `servicefee` varchar(255) DEFAULT NULL,
  `carfee` varchar(255) DEFAULT NULL,
  `motofee` varchar(255) DEFAULT NULL,
  `overtimefee` varchar(255) DEFAULT NULL,
  `waterfee` varchar(255) DEFAULT NULL,
  `electricityfee` varchar(255) DEFAULT NULL,
  `deposit` varchar(255) DEFAULT NULL,
  `payment` varchar(255) DEFAULT NULL,
  `renttime` varchar(255) DEFAULT NULL,
  `decorationtime` varchar(255) DEFAULT NULL,
  `brokeragetee` decimal(13,2) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `linkofbuilding` varchar(255) DEFAULT NULL,
  `map` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `createddate` datetime DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  `fileid` bigint DEFAULT NULL,
  `status` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_BuildingFile` (`fileid`),
  CONSTRAINT `FK_BuildingFile` FOREIGN KEY (`fileid`) REFERENCES `file` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--

LOCK TABLES `building` WRITE;
/*!40000 ALTER TABLE `building` DISABLE KEYS */;
INSERT INTO `building` VALUES (1,'Nam Giao Building Tower','59 phan xích long','Phường 2','QUAN_1','',3,550,'','',15,'15 triệu/m2','150k','','','',NULL,'','','','','',NULL,'TANG_TRET,NGUYEN_CAN',NULL,NULL,NULL,NULL,'2022-08-27 00:00:00','2022-09-05 18:41:20',NULL,'admin',NULL,'CHUA_DUOC_THUE'),(2,'ACM Tower','96 cao thắng','Phường 4','QUAN_2','',2,650,'','',18,'18 triệu/m2','259k','','','',NULL,'','','','','',NULL,'NGUYEN_CAN',NULL,NULL,NULL,NULL,'2022-08-01 19:43:16','2022-08-28 12:05:14',NULL,'nguyenvana',NULL,'CHUA_DUOC_THUE'),(3,'Alpha 2 Building Tower','154 nguyễn đình chiểu','Phường 6','QUAN_1','',1,200,'','',20,'20 triệu/m2','1123k','','','',NULL,'','','','','',NULL,'NOI_THAT',NULL,NULL,NULL,NULL,'2022-07-27 19:43:16','2022-09-05 23:21:51',NULL,'admin',NULL,'CHUA_DUOC_THUE'),(4,'IDD 1 Building','111 Lý Chính Thắng','Phường 7','QUAN_4','',1,200,'','',12,'12 triệu/m2','12k','','','',NULL,'','','','','',NULL,'NOI_THAT,TANG_TRET,NGUYEN_CAN',NULL,NULL,NULL,NULL,'2022-08-26 19:43:16','2022-09-03 16:35:13',NULL,'admin',NULL,'CHUA_DUOC_THUE'),(64,'SOICT','16 Tạ Quang Bửu','Bách Khoa','QUAN_2','Được',1,400,'Đông','1',20,'20 triệu','5 Triệu','5 Triệu','5 Triệu','5 Triệu',NULL,'5 Triệu','10 Triệu','Trước','8/2022-8/2023','9/2022',1.00,'NGUYEN_CAN',NULL,NULL,NULL,NULL,'2022-09-01 23:34:15','2022-09-03 16:34:28','admin','admin',NULL,'DA_DUOC_THUE'),(65,'Kepler Garry ','20A Gia Thụy','Ngọc Lâm','QUAN_5','Chưa rõ',2,501,'bắc','2',12,'12 triệu','200k','2 triệu','100k','500k',NULL,'1 triệu','12 triệu','Trả góp','1 năm','9/12',1000.00,'NOI_THAT,TANG_TRET',NULL,NULL,NULL,NULL,'2022-09-02 23:52:12','2022-09-07 14:54:51','admin','admin',NULL,'CHUA_DUOC_THUE'),(76,'BMX','203 Đào Duy Từ','Đồng Xuân','QUAN_3','',NULL,NULL,'','',NULL,'','','','','',NULL,'','','','','',NULL,'TANG_TRET',NULL,NULL,NULL,NULL,'2022-09-03 09:18:17','2022-09-07 09:18:17','admin','admin',NULL,'CHUA_DUOC_THUE'),(77,'Messa','125 Lê Văn Lương','Định Công','QUAN_6','',2,NULL,'','',NULL,'','','','','',NULL,'','','','','',9000.00,'NGUYEN_CAN',NULL,NULL,NULL,NULL,'2022-09-02 09:29:32','2022-09-07 09:29:32','admin','admin',NULL,'CHUA_DUOC_THUE'),(78,'Hạ Đình Tower','222 Bạch Mai','Bạch Mai','QUAN_2','',NULL,100,'Tây','',6,'6 triệu','','','','',NULL,'','','','','',NULL,'NGUYEN_CAN',NULL,NULL,NULL,NULL,'2022-09-02 09:51:08','2022-09-07 17:38:26','admin','admin',NULL,'DA_DUOC_THUE'),(79,'Long Nam ','Thái Hà','Trung Liệt','QUAN_1','',1,120,'','',13,'','','','','',NULL,'','','','','',NULL,'TANG_TRET',NULL,NULL,NULL,NULL,'2022-09-01 09:53:02','2022-09-07 17:37:11','admin','admin',NULL,'DA_DUOC_THUE'),(80,'Becamex Building','222 Lê Duẩn','Khâm Thiên','QUAN_1','Đẹp',2,102,'Nam','A',30,'30 triệu','1 triệu','1 triệu','1 triệu','1 triệu',NULL,'1 triệu','1 triệu','sau','2 năm','10/10',2222.00,'NGUYEN_CAN',NULL,NULL,NULL,NULL,'2022-09-04 09:58:05','2022-09-07 09:58:05','admin','admin',NULL,'CHUA_DUOC_THUE'),(87,'Tòa Long Thành','9 Phố Huế','Hàng Bông','QUAN_3','',NULL,NULL,'','',NULL,'','','','','',NULL,'','','','','',NULL,'TANG_TRET',NULL,NULL,NULL,NULL,'2022-09-05 16:42:21','2022-09-07 16:42:21','admin','admin',NULL,'CHUA_DUOC_THUE');
/*!40000 ALTER TABLE `building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fullname` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `createddate` datetime DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  `note` text,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (14,'Anh Nguyễn Bảo Long','0982193840','longbaonguyen_cus@gmail.com','2022-08-02 23:41:38','2022-09-07 17:17:27','admin','nguyenvanc','Khu vực Thanh Xuân, 100m2','RENTED'),(15,'Chị Na','0129210222','anhxeo123@gmail.com','2022-08-06 16:37:57','2022-09-07 17:28:31','admin','daoduykien','Dưới 15 triệu','STOP_SUPPORT'),(23,'Chú Lên Văn Bảy','0628898333','bayle@gmail.com','2022-08-12 10:38:00','2022-09-07 10:38:00','admin','admin','Thanh Xuân, 10 triệu','WAITING_FOR_SUPPORT'),(24,'Chị Hồng BCM','0329218409','hong3932@bcm.com','2022-08-13 10:41:02','2022-09-07 17:21:55','admin','nguyenvana','Nguyên Căn, Hoàng Mai','BEING_SUPPORTED'),(25,'Anh Lê Xuân Thành','0882982889','','2022-08-15 10:41:47','2022-09-07 14:41:55','admin','admin','Đường Lê Duẩn','BEING_SUPPORTED'),(26,'Ông Trịnh Văn Hiệp','0129545327','trinhvanhiepp@gmail.com','2022-08-21 10:43:26','2022-09-07 14:41:44','admin','admin','Phường Bạch Mai, trên 100m2','WAITING_FOR_RESPONSE'),(27,'Chị Hoàng Ngân','0129292400','nganngue@gmail.com','2022-09-01 15:50:34','2022-09-07 17:35:56','admin','admin','Trung Liệt, có tầng hầm, nguyên căn','BEING_SUPPORTED'),(28,'Bác Hải','0987919444','hainguyen@gmail.com','2022-09-01 16:37:05','2022-09-07 16:37:05','admin','admin','Hướng đông','WAITING_FOR_SUPPORT'),(30,'Anh Bích','0238199422','bichle@gmail.com','2022-09-02 17:41:42','2022-09-07 17:41:42','admin','admin','','WAITING_FOR_SUPPORT'),(31,'Anh Tâm PCA','0812828182','tam41212@pca.com','2022-09-02 17:42:38','2022-09-07 17:42:38','admin','admin','Láng Hạ','WAITING_FOR_SUPPORT'),(32,'Chị Mai Anh','0999274185','m.anh@gmail.com','2022-09-02 17:43:26','2022-09-07 17:45:36','admin','nguyenvanc','có nội thất','WAITING_FOR_RESPONSE');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `originalname` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `generatedname` varchar(255) DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `createddate` datetime DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `generatedname_UNIQUE` (`generatedname`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rentarea`
--

DROP TABLE IF EXISTS `rentarea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rentarea` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `value` int DEFAULT NULL,
  `buildingid` bigint DEFAULT NULL,
  `createddate` datetime DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `rentarea_building` (`buildingid`),
  CONSTRAINT `rentarea_building` FOREIGN KEY (`buildingid`) REFERENCES `building` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=331 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rentarea`
--

LOCK TABLES `rentarea` WRITE;
/*!40000 ALTER TABLE `rentarea` DISABLE KEYS */;
INSERT INTO `rentarea` VALUES (297,300,77,'2022-09-07 09:29:32','2022-09-07 09:29:32','admin','admin'),(298,800,77,'2022-09-07 09:29:32','2022-09-07 09:29:32','admin','admin'),(299,900,77,'2022-09-07 09:29:32','2022-09-07 09:29:32','admin','admin'),(300,700,3,'2022-09-07 09:30:41','2022-09-07 09:30:41','admin','admin'),(301,760,76,'2022-09-07 09:30:56','2022-09-07 09:30:56','admin','admin'),(302,100,2,'2022-09-07 09:32:50','2022-09-07 09:32:50','admin','admin'),(303,800,2,'2022-09-07 09:32:50','2022-09-07 09:32:50','admin','admin'),(304,900,2,'2022-09-07 09:32:50','2022-09-07 09:32:50','admin','admin'),(305,200,4,'2022-09-07 09:33:19','2022-09-07 09:33:19','admin','admin'),(306,650,4,'2022-09-07 09:33:19','2022-09-07 09:33:19','admin','admin'),(307,400,64,'2022-09-07 09:49:10','2022-09-07 09:49:10','admin','admin'),(308,760,64,'2022-09-07 09:49:10','2022-09-07 09:49:10','admin','admin'),(314,200,80,'2022-09-07 09:58:05','2022-09-07 09:58:05','admin','admin'),(327,500,65,'2022-09-07 14:54:51','2022-09-07 14:54:51','admin','admin'),(328,150,87,'2022-09-07 16:42:21','2022-09-07 16:42:21','admin','admin'),(329,320,79,'2022-09-07 17:37:11','2022-09-07 17:37:11','admin','admin'),(330,200,78,'2022-09-07 17:38:26','2022-09-07 17:38:26','admin','admin');
/*!40000 ALTER TABLE `rentarea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `createddate` datetime DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Quản lý','manager',NULL,NULL,NULL,NULL),(2,'Nhân viên','staff',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `customerid` bigint NOT NULL,
  `createddate` datetime DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customer_transaction` (`customerid`),
  CONSTRAINT `fk_customer_transaction` FOREIGN KEY (`customerid`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (24,'TU_VAN','Tư vấn',14,'2022-09-03 15:50:37','2022-09-03 15:50:37','admin','admin'),(50,'TU_VAN','Gọi điện tư vấn',26,'2022-09-05 10:45:47','2022-09-07 10:45:47','daoduykien','daoduykien'),(51,'DAN_DI_XEM','Đưa đi xem DMA Building',26,'2022-09-07 10:54:57','2022-09-07 10:54:57','levanxuan','levanxuan'),(52,'TU_VAN','Gọi điện tư vấn',25,'2022-09-07 10:59:55','2022-09-07 10:59:55','levanxuan','levanxuan'),(54,'DAN_DI_XEM','Đưa đi xem tòa SOICT',14,'2022-09-07 17:10:34','2022-09-07 17:10:34','nguyenvanc','nguyenvanc'),(59,'TU_VAN','Gọi điện tư vấn',24,'2022-09-06 13:33:44','2022-09-07 17:18:49','nguyenvand','nguyenvand'),(60,'TU_VAN','Gọi điện tiếp',24,'2022-09-07 17:19:33','2022-09-07 17:19:33','nguyenvana','nguyenvana'),(61,'TU_VAN','Gọi điện tư vấn',15,'2022-08-31 07:26:51','2022-08-31 07:26:51','daoduykien','daoduykien'),(62,'TU_VAN','Hết quan tâm',15,'2022-09-07 17:28:26','2022-09-07 17:28:26','daoduykien','daoduykien'),(63,'TU_VAN','Gọi điện tư vấn',27,'2022-09-04 16:33:35','2022-09-07 17:33:35','admin','admin'),(64,'TU_VAN','Gọi điện tư vấn',32,'2022-09-03 09:44:49','2022-09-07 17:44:49','nguyenvanc','nguyenvanc'),(65,'DAN_DI_XEM','Đưa đi xem',32,'2022-09-03 15:33:57','2022-09-07 17:44:57','nguyenvanc','nguyenvanc');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` int NOT NULL,
  `createddate` datetime DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','$2a$10$5X6g6F6GdCVMIfYLCo4eguIoxFzXR7yj5S51hinVZ/aN9WZqw0ukm','Nguyễn Tuấn Anh','0329218897','keplergarry@mail.com',1,NULL,'2022-09-07 10:28:12',NULL,'admin'),(2,'admin2','$2a$10$ERUyEVU3Ht6lLv8QgypJUeUtfcz6lKqh8OiIwTNEBBiHhCAZGIXxK','Nguyễn Văn Admin','0329218890','admin@gmail.com',1,NULL,'2022-09-02 21:16:13',NULL,'nguyenvana'),(23,'nguyenvana','$2a$10$uugKWx2qfjKtaPsZJPOpMeztOJiSiYG0M4JCoF/XVvGZXzsx1t9O2','Nguyễn Văn A','0322832911','nguyenvana@gmail.com',1,'2022-09-03 16:30:21','2022-09-03 16:30:21','admin','admin'),(24,'nguyenvanb','$2a$10$bINnVLlysDsO54O3vaMvAuYSVoixJSBbKI3E9.Ahh9DpSluVzG0tG','Nguyễn Văn B','0987332172','bnguyenvan@gmail.com',1,'2022-09-03 16:33:05','2022-09-03 16:33:33','admin','admin'),(25,'nguyenvanc','$2a$10$a9w5YCdDgtkYgL5ZKekYXOpvr9BEKwcv1w90L5pHFv07HYZX.FYqC','Nguyễn Văn C','0192111921','nguyenvance@domain.com',1,'2022-09-06 10:08:47','2022-09-07 10:32:24','admin','admin'),(27,'lockeduser','$2a$10$8neAWx7Io5qUKs4A.vd.9eJG42bK.XEVVuANK13H9kDWZmX9qGR1m','locked user','0232218897','anhdasw2@gmail.com',0,'2022-09-06 10:18:31','2022-09-07 10:48:37','admin','admin'),(28,'levanxuan','$2a$10$U6rczzEF/1IC8yXK3I56kOw/havLFKrBSiIV2kMDyfwfxxRvTi2Eu','Lê Văn Xuân','0912222911','xuanvanle129@gmail.com',1,'2022-09-06 09:19:59','2022-09-07 09:19:59','admin','admin'),(29,'daoduykien','$2a$10$BBbuhZhrXImQpXhSzpmpoenEQMS1Rcty31eBxLfc0M1z8ujHgjscW','Đào Duy Kiên','0923853247','kiendao29@gmail.com',1,'2022-09-07 09:20:29','2022-09-07 09:53:37','admin','admin'),(30,'nguyenvand','$2a$10$PzgGfa1.5Tf9ZEP4I/2DhO1vGy6f5Lbb93M1LseK4ILifj1v9cTo2','Nguyễn Văn D','0238583977','dnguyen@outlook.com',1,'2022-09-07 09:54:35','2022-09-07 14:53:00','admin','nguyenvand'),(31,'lethina','$2a$10$AC9vAXFCtMlWVNU0OnCgfuni3565XSUak1vVUAV3Mm3p8rTS5nmm6','Lê Thị Na','0989384844','nalethi@gmail.com',1,'2022-09-07 16:38:02','2022-09-07 16:39:37','admin','admin');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `roleid` bigint NOT NULL,
  `userid` bigint NOT NULL,
  `createddate` datetime DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_role` (`userid`),
  KEY `fk_role_user` (`roleid`),
  CONSTRAINT `fk_role_user` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1,1,NULL,NULL,NULL,NULL),(2,1,2,NULL,NULL,NULL,NULL),(94,2,23,NULL,NULL,NULL,NULL),(96,2,24,NULL,NULL,NULL,NULL),(104,2,25,NULL,NULL,NULL,NULL),(105,2,28,NULL,NULL,NULL,NULL),(109,2,29,NULL,NULL,NULL,NULL),(114,2,27,NULL,NULL,NULL,NULL),(115,2,30,NULL,NULL,NULL,NULL),(117,2,31,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-07 20:23:30
