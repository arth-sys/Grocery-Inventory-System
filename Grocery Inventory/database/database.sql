-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: grocery_inventory
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(100) DEFAULT NULL,
  `number` mediumtext,
  `PASSWORD` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Arth','1234567890','123456'),(2,'Tanmay','987654321','098765'),(3,'Yash','1234554321','123455'),(4,'Devdatta','1357924680','135792');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordered_items`
--

DROP TABLE IF EXISTS `ordered_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordered_items` (
  `order_item_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `Price_At_Purchase` double DEFAULT NULL,
  PRIMARY KEY (`order_item_id`),
  KEY `product_id` (`product_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `ordered_items_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `ordered_items_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordered_items`
--

LOCK TABLES `ordered_items` WRITE;
/*!40000 ALTER TABLE `ordered_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordered_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(40) DEFAULT NULL,
  `category` varchar(40) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  `sales` int DEFAULT NULL,
  `discount` double DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Milk(1L)','Dairy',60,130,'2026-04-25',NULL,NULL),(2,'Bread(12 pc)','Bakery',40,78,'2026-04-13',NULL,NULL),(3,'Eggs(12 pcs)','Dairy',90,198,'2026-04-25',NULL,NULL),(4,'Butter(500g)','Dairy',250,60,'2026-06-15',NULL,NULL),(5,'Cheese Slice(5pcs)','Dairy',180,50,'2026-06-30',NULL,NULL),(6,'Yogurt(200g)','Dairy',50,90,'2026-04-30',NULL,NULL),(7,'paneer(200g)','Dairy',85,70,'2026-04-25',NULL,NULL),(8,'Apple(1Kg)','Fruits',150,100,'2026-04-25',NULL,NULL),(9,'Banana(1Kg)','Fruits',60,150,'2026-04-14',NULL,NULL),(10,'Orange(1Kg)','Fruits',120,80,'2026-04-21',NULL,NULL),(11,'Grapes(1Kg)','Fruits',90,60,'2026-04-22',NULL,NULL),(12,'Mango(1Kg)','Fruits',200,70,'2026-04-28',NULL,NULL),(13,'Tomato(1Kg)','Vegetables',40,120,'2026-04-28',NULL,NULL),(14,'Potato(1Kg)','Vegetables',30,200,'2026-05-07',NULL,NULL),(15,'Onion(1Kg)','Vegetables',35,180,'2026-05-16',NULL,NULL),(16,'Carrot(1Kg)','Vegetables',50,90,'2026-04-20',NULL,NULL),(17,'Spinach(1Kg)','Vegetables',25,60,'2026-04-16',NULL,NULL),(18,'Cabbage(1Kg)','Vegetables',40,75,'2026-04-21',NULL,NULL),(19,'Rice(5Kg)','Grains',350,100,'2027-04-10',NULL,NULL),(20,'Wheat Flour(5Kg)','Grains',300,120,'2027-04-16',NULL,NULL),(21,'Oats','Grains',180,70,'2027-02-01',NULL,NULL),(22,'Cornflakes(200g)','Cereals',80,120,'2026-02-01',NULL,NULL),(23,'Chocos(200g)','Cereals',95,120,'2026-02-01',NULL,NULL),(24,'Sugar(1kg)','Grocery',45,200,'2026-05-01',NULL,NULL),(25,'Salt(1kg)','Grocery',20,300,'2028-01-10',NULL,NULL),(26,'Cooking Oil(1L)','Grocery',180,110,'2026-09-19',NULL,NULL),(27,'Tea Powder(500 g)','Beverages',120,170,'2026-11-27',NULL,NULL),(28,'Coffee Powder(500 g)','Beverages',300,50,'2026-10-16',NULL,NULL),(29,'Biscuits(100 g)','Snacks',30,200,'2026-07-10',NULL,NULL),(30,'Potato chips(100 g)','Snacks',20,250,'2026-07-01',NULL,NULL),(31,'Namkeen mix(350 g)','Snacks',60,100,'2026-08-06',NULL,NULL),(32,'Instant noodeles(150 g)','Packaged Food',15,300,'2026-08-03',NULL,NULL),(33,'Pasta(500 g)','Packaged Food',120,90,'2026-12-03',NULL,NULL),(34,'Ketchup(500 g)','Sauces',140,70,'2026-11-15',NULL,NULL),(35,'Mayonnaise(1kg)','Sauces',180,60,'2026-10-15',NULL,NULL),(36,'Frozen peas(1kg)','Frozen',120,80,'2026-08-03',NULL,NULL),(37,'Ice cream(1kg)','Frozen',250,40,'2026-07-03',NULL,NULL),(38,'Chicken(1kg)','Meat',280,50,'2026-04-26',NULL,NULL),(39,'Fish(1kg)','Meat',300,40,'2026-04-26',NULL,NULL),(40,'Mutton(1kg)','Meat',700,30,'2026-04-26',NULL,NULL),(41,'Soft drinks(1L)','Cold drinks',70,120,'2026-12-03',NULL,NULL),(42,'Juice(1L)','Cold drinks',110,100,'2026-12-03',NULL,NULL),(43,'Water(1L)','Cold drinks',18,295,'2026-12-03',NULL,10),(44,'Dishwash(1L)','Household',120,70,'2027-03-01',NULL,NULL),(45,'Detergent(1L)','Household',250,90,'2027-06-01',NULL,NULL),(46,'Handwash(200mL)','Care',90,100,'2027-02-01',NULL,NULL),(47,'Shampoo(200mL)','Care',187.5,80,'2027-09-01',NULL,25),(48,'Soap bar(100g)','Care',40,200,'2027-04-01',NULL,NULL),(49,'Toothpaste(100g)','Care',120,150,'2027-05-01',NULL,NULL),(50,'Tissue paper(50 pcs)','Household',80,110,'2028-01-01',NULL,NULL),(51,'garbage bags(25 pcs)','Household',100,89,'2028-01-01',NULL,NULL),(52,'Laundry bag(1pcs)','Household',250,200,'2030-04-04',NULL,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suggestion`
--

DROP TABLE IF EXISTS `suggestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suggestion` (
  `suggestion` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suggestion`
--

LOCK TABLES `suggestion` WRITE;
/*!40000 ALTER TABLE `suggestion` DISABLE KEYS */;
/*!40000 ALTER TABLE `suggestion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-16 10:47:06
