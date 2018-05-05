DROP DATABASE IF EXISTS filmoteka;
CREATE DATABASE  IF NOT EXISTS `filmoteka` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `filmoteka`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: filmoteka
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genres` (
  `genre_id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT 'Primary key - GENRE ID',
  `value` varchar(45) NOT NULL COMMENT 'Genre''s name',
  PRIMARY KEY (`genre_id`),
  UNIQUE KEY `values_unique` (`value`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
INSERT INTO `genres` VALUES (1,'ACTION'),(2,'ADVENTURE'),(3,'ANIMATION'),(4,'BIOGRAPHY'),(5,'COMEDY'),(6,'CRIME'),(7,'DOCUMENTARY'),(8,'DRAMA'),(9,'FAMILY'),(10,'FANTASY'),(11,'HISTORY'),(12,'HORROR'),(13,'MUSIC'),(14,'MUSICAL'),(15,'MYSTERY'),(16,'ROMANCE'),(17,'SCIFI'),(18,'SPORT'),(19,'SUPERHERO'),(20,'THRILLER'),(21,'WAR'),(22,'WESTERN');
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movies` (
  `product_id` int(11) NOT NULL,
  `director` varchar(80) DEFAULT NULL COMMENT 'Movie''s director',
  PRIMARY KEY (`product_id`),
  KEY `fk_MOVIE_Product1_idx` (`product_id`),
  CONSTRAINT `fk_MOVIE_Product1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (1,'James Cameron'),(2,'James Cameron'),(3,'Joe Johnston'),(7,NULL),(8,'Ryan Coogler');
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_has_products`
--

DROP TABLE IF EXISTS `order_has_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_has_products` (
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `validity` date DEFAULT NULL,
  PRIMARY KEY (`order_id`,`product_id`),
  KEY `fk_order_has_products_PRODUCTS1_idx` (`product_id`),
  CONSTRAINT `fk_order_has_products_ORDERS1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_has_products_PRODUCTS1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_has_products`
--

LOCK TABLES `order_has_products` WRITE;
/*!40000 ALTER TABLE `order_has_products` DISABLE KEYS */;
INSERT INTO `order_has_products` VALUES (1,1,NULL),(2,4,'2018-04-24'),(8,3,'2018-05-07'),(8,5,NULL),(8,6,NULL),(17,4,'2018-05-10');
/*!40000 ALTER TABLE `order_has_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT 'Owner''s (user) ID',
  `date` date NOT NULL COMMENT 'Order''s date',
  `total_cost` decimal(9,2) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_ORDER_User1_idx` (`user_id`),
  KEY `DATE_INDEX` (`date`),
  KEY `COST_INDEX` (`total_cost`),
  CONSTRAINT `fk_ORDER_User1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,'2018-04-16',5.00),(2,1,'2018-04-16',5.00),(8,2,'2018-04-29',13.28),(17,3,'2018-05-02',3.00);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_categories`
--

DROP TABLE IF EXISTS `product_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_categories` (
  `category_id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_categories`
--

LOCK TABLES `product_categories` WRITE;
/*!40000 ALTER TABLE `product_categories` DISABLE KEYS */;
INSERT INTO `product_categories` VALUES (1,'Movie'),(2,'TV Series');
/*!40000 ALTER TABLE `product_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_has_genres`
--

DROP TABLE IF EXISTS `product_has_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_has_genres` (
  `product_id` int(11) NOT NULL,
  `genre_id` tinyint(4) NOT NULL,
  PRIMARY KEY (`product_id`,`genre_id`),
  KEY `fk_PRODUCT_HAS_GENRE_GENRE1_idx` (`genre_id`),
  CONSTRAINT `GENRE_HAS_PRODUCT` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`genre_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `PRODUCT_HAS_GENRE` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_has_genres`
--

LOCK TABLES `product_has_genres` WRITE;
/*!40000 ALTER TABLE `product_has_genres` DISABLE KEYS */;
INSERT INTO `product_has_genres` VALUES (1,1),(7,1),(8,1),(3,2),(7,2),(8,2),(9,2),(4,3),(9,3),(4,5),(9,5),(6,6),(10,6),(2,8),(5,8),(6,8),(10,8),(3,9),(3,10),(6,15),(10,15),(2,16),(5,16),(1,17),(7,17),(8,17);
/*!40000 ALTER TABLE `product_has_genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_has_raters`
--

DROP TABLE IF EXISTS `product_has_raters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_has_raters` (
  `product_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `rating` decimal(3,1) NOT NULL,
  PRIMARY KEY (`product_id`,`user_id`),
  KEY `fk_PRODUCT_HAS_RATERS_User1_idx` (`user_id`),
  CONSTRAINT `fk_PRODUCT_HAS_RATERS_Product1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_PRODUCT_HAS_RATERS_User1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_has_raters`
--

LOCK TABLES `product_has_raters` WRITE;
/*!40000 ALTER TABLE `product_has_raters` DISABLE KEYS */;
INSERT INTO `product_has_raters` VALUES (1,1,1.0),(1,2,4.8),(1,3,3.0),(3,3,1.0),(4,1,7.0),(4,2,4.6),(4,3,1.0),(6,1,1.0),(6,3,10.0);
/*!40000 ALTER TABLE `product_has_raters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary key за таблицата с продуктите (филми и сериали)',
  `category_id` tinyint(4) NOT NULL COMMENT 'Product''s category ID',
  `name` varchar(150) NOT NULL COMMENT 'Product name',
  `release_year` year(4) NOT NULL COMMENT 'The year the product was released',
  `pg_rating` varchar(10) NOT NULL COMMENT 'PG rating',
  `duration` smallint(6) NOT NULL COMMENT 'Duration of the product in minutes',
  `rent_cost` decimal(7,2) NOT NULL COMMENT 'Rent cost in BGN',
  `buy_cost` decimal(7,2) NOT NULL COMMENT 'Buy cost in BGN',
  `description` varchar(1000) DEFAULT NULL COMMENT 'Description for a product. The length may be longer - most descriptions in the more popular web portals are around 200 symbols.',
  `poster` varchar(200) DEFAULT NULL COMMENT 'Path to the poster of the product.',
  `trailer` varchar(200) DEFAULT NULL COMMENT 'Path to the product''s trailer',
  `writers` varchar(200) DEFAULT NULL COMMENT 'Writers',
  `actors` varchar(1000) DEFAULT NULL COMMENT 'Actors',
  `sale_percent` decimal(2,0) DEFAULT '0',
  `sale_validity` date DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `NAME_INDEX` (`name`),
  KEY `RELEASE_YEAR` (`release_year`),
  KEY `fk_category_id_idx` (`category_id`),
  CONSTRAINT `fk_category_id` FOREIGN KEY (`category_id`) REFERENCES `product_categories` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,1,'The Terminator',1984,'PG-R',107,5.00,10.00,'A seemingly indestructible humanoid cyborg is sent from 2029 to 1984 to assassinate a waitress, whose unborn son will lead humanity in a war against the machines, while a soldier from that war is sent to protect her at all costs.','1.jpg','terminatorTrailer.avi','James Cameron, Gale Anne Hurd','Arnold Schwarzenegger, Linda Hamilton, Michael Biehn',10,NULL),(2,1,'Titanic',1997,'PG-13',194,10.00,15.00,'84 years later, a 100 year-old woman named Rose DeWitt Bukater tells the story to her granddaughter Lizzy Calvert, Brock Lovett, Lewis Bodine, Bobby Buell and Anatoly Mikailavich on the Keldysh about her life set in April 10th 1912, on a ship called Titanic when young Rose boards the departing ship with the upper-class passengers and her mother, Ruth DeWitt Bukater, and her fiancé, Caledon Hockley. Meanwhile, a drifter and artist named Jack Dawson and his best friend Fabrizio De Rossi win third-class tickets to the ship in a game. And she explains the whole story from departure until the death of Titanic on its first and last voyage April 15th, 1912 at 2:20 in the morning.','2.jpg','https://www.youtube.com/embed/2e-eXJ6HgkQ','James Cameron','Leonardo DiCaprio, Kate Winslet, Billy Zane',5,'2018-03-04'),(3,1,'Jumanji',1995,'PG-12',104,5.00,8.00,'After being trapped in a jungle board game for 26 years, a Man-Child wins his release from the game. But, no sooner has he arrived that he is forced to play again, and this time sets the creatures of the jungle loose on the city. Now it is up to him to stop them.','3.jpg','jumanjiTrailer.avi','Jonathan Hensleigh, Greg Taylor','Robin Williams, Kirsten Dunst, Bonnie Hunt',3,'2018-05-15'),(4,2,'The Simpsons',1989,'PG-12',22,3.00,5.00,'This is an animated sitcom about the antics of a dysfunctional family. Homer is the oafish unhealthy beer loving father, Marge is the hardworking homemaker wife, Bart is the perpetual ten-year-old underachiever (and proud of it), Lisa is the unappreciated eight-year-old genius, and Maggie is the cute, pacifier loving silent infant.','4.jpg','simpsonsTrailer.avi','James L. Brooks, Matt Groening, Sam Simon','Dan Castellaneta, Nancy Cartwright, Julie Kavner',1,NULL),(5,2,'ER',1994,'PG-16',44,3.00,5.00,'Michael Crichton has created a medical drama that chronicles life and death in a Chicago hospital emergency room. Each episode tells the tale of another day in the ER, from the exciting to the mundane, and the joyous to the heart-rending. Frenetic pacing, interwoven plot lines, and emotional rollercoastering is used to attempt to accurately depict the stressful environment found there. This show even portrays the plight of medical students in their quest to become physicians.','5.jpg',NULL,'Michael Crichton','Anthony Edwards, George Clooney, Julianna Margulies',22,NULL),(6,2,'The Mentalist',2008,'PG-14',43,3.00,5.00,'After a serial killer named Red John murdered Patrick Jane\'s wife and daughter, Jane dedicated his life to hunting down and killing Red John. To that end he gave up his lucrative pretense of being a psychic and joined the California Bureau of Investigation (CBI) as a consultant to the team responsible for investigating the Red John case, led by Senior Agent Teresa Lisbon. Using Jane\'s exceptional gift for observation and his mentalist tric able to close an unprecedented number of cases, but Jane\'s unconventional and often outright illegal methods also bring much censure down on Lisbon\'s head, making his assistance both a blessing and a curse. Meanwhile, the hunt for Red John continues...','6.jpg',NULL,'Bruno Heller','Simon Baker, Robin Tunney, Tim Kang',0,NULL),(7,1,'Ready Player One',2018,'PG-13',140,10.00,30.00,'In the year 2045, the real world is a harsh place. The only time Wade Watts (Tye Sheridan) truly feels alive is when he escapes to the OASIS, an immersive virtual universe where most of humanity spends their days. In the OASIS, you can go anywhere, do anything, be anyone-the only limits are your own imagination. The OASIS was created by the brilliant and eccentric James Halliday (Mark Rylance), who left his immense fortune and total control of the Oasis to the winner of a three-part contest he designed to find a worthy heir. When Wade conquers the first challenge of the reality-bending treasure hunt, he and his friends-aka the High Five-are hurled into a fantastical universe of discovery and danger to save the OASIS.','ready-player-one-poster.jpg','https://www.youtube.com/embed/cSp1dM2Vj48','Zak Penn, Ernest Cline',' Tye Sheridan, Olivia Cooke, Ben Mendelsohn',0,NULL),(8,1,'Black Panther',2018,'PG-13',134,7.00,15.00,'After the events of Captain America: Civil War, King T\'Challa returns home to the reclusive, technologically advanced African nation of Wakanda to serve as his country\'s new leader. However, T\'Challa soon finds that he is challenged for the throne from factions within his own country. When two foes conspire to destroy Wakanda, the hero known as Black Panther must team up with C.I.A. agent Everett K. Ross and members of the Dora Milaje, Wakandan special forces, to prevent Wakanda from being dragged into a world war.','Black_Panther.jpg','https://www.youtube.com/embed/fsT5SyBLlIg','Ryan Coogler, Joe Robert Cole',' Chadwick Boseman, Michael B. Jordan, Lupita Nyong\'o',15,'2018-06-02'),(9,2,'Rick and Morty',2013,'PG-12',23,4.00,10.00,'An animated series on adult-swim about the infinite adventures of Rick, a genius alcoholic and careless scientist, with his grandson Morty, a 14 year-old anxious boy who is not so smart. Together, they explore the infinite universes; causing mayhem and running into trouble.','RickAndMorty.jpg','https://www.youtube.com/embed/_uUcMwsR5hg','Dan Harmon, Justin Roiland','Justin Roiland, Chris Parnell, Spencer Grammer',0,NULL),(10,2,'Sherlock',2010,'PG-12',88,7.00,14.00,'In this modernized version of the Conan Doyle characters, using his detective plots, Sherlock Holmes lives in early 21st century London and acts more cocky towards Scotland Yard\'s detective inspector Lestrade because he\'s actually less confident. Doctor Watson is now a fairly young veteran of the Afghan war, less adoring and more active.','sherlock_poster.jpg','https://www.youtube.com/embed/lAai7yjv6v4','Mark Gatiss, Steven Moffat','Benedict Cumberbatch, Martin Freeman, Una Stubbs',33,'2018-05-26');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reviews` (
  `review_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  KEY `fk_reviews_products` (`product_id`),
  KEY `fk_reviews_users` (`user_id`),
  CONSTRAINT `fk_reviews_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_reviews_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (22,1,1,'I really liked this movie.','2018-04-29 16:50:29'),(23,1,1,'fine','2018-04-29 16:51:05'),(24,4,3,'I\'ve always enjoyed watching Homer.','2018-05-03 10:33:48'),(25,4,1,'5/7 would watch again.','2018-05-03 10:48:02'),(26,6,3,'Nooo','2018-05-05 02:11:25'),(27,6,3,'hello\n','2018-05-05 02:25:12');
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tvseries`
--

DROP TABLE IF EXISTS `tvseries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tvseries` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `season` tinyint(4) NOT NULL,
  `finished_airing` date DEFAULT NULL COMMENT 'TVSeries'' date of last episode airing',
  PRIMARY KEY (`product_id`),
  KEY `fk_tvseries_products_idx` (`product_id`),
  CONSTRAINT `fk_tvseries_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tvseries`
--

LOCK TABLES `tvseries` WRITE;
/*!40000 ALTER TABLE `tvseries` DISABLE KEYS */;
INSERT INTO `tvseries` VALUES (4,1,NULL),(5,1,NULL),(6,1,NULL),(9,1,NULL),(10,1,NULL);
/*!40000 ALTER TABLE `tvseries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_favorite_products`
--

DROP TABLE IF EXISTS `user_has_favorite_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_favorite_products` (
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`product_id`),
  KEY `fk_USER_HAS_FAVORITE_PRODUCTS_Product1_idx` (`product_id`),
  CONSTRAINT `fk_USER_HAS_FAVORITE_PRODUCTS_Product1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_USER_HAS_FAVORITE_PRODUCTS_User1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_favorite_products`
--

LOCK TABLES `user_has_favorite_products` WRITE;
/*!40000 ALTER TABLE `user_has_favorite_products` DISABLE KEYS */;
INSERT INTO `user_has_favorite_products` VALUES (3,1),(1,2),(2,2),(1,5),(3,8);
/*!40000 ALTER TABLE `user_has_favorite_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_products`
--

DROP TABLE IF EXISTS `user_has_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_products` (
  `product_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `validity` date DEFAULT NULL COMMENT 'Validity for a product - null if bought and a date if rented',
  PRIMARY KEY (`product_id`,`user_id`),
  KEY `fk_USER_HAS_PRODUCT_User1_idx` (`user_id`),
  CONSTRAINT `fk_USER_HAS_PRODUCT_Product1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_USER_HAS_PRODUCT_User1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_products`
--

LOCK TABLES `user_has_products` WRITE;
/*!40000 ALTER TABLE `user_has_products` DISABLE KEYS */;
INSERT INTO `user_has_products` VALUES (1,1,'2018-05-02'),(3,1,'2018-05-02'),(3,2,'2018-05-22'),(3,3,'2018-05-10'),(4,3,'2018-05-10'),(5,1,NULL),(6,1,NULL),(6,3,NULL);
/*!40000 ALTER TABLE `user_has_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_watchlist_products`
--

DROP TABLE IF EXISTS `user_has_watchlist_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_watchlist_products` (
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`product_id`),
  KEY `fk_USER_HAS_WATCHLIST_PRODUCTS_Product1_idx` (`product_id`),
  CONSTRAINT `fk_USER_HAS_WATCHLIST_PRODUCTS_Product1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_USER_HAS_WATCHLIST_PRODUCTS_User1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_watchlist_products`
--

LOCK TABLES `user_has_watchlist_products` WRITE;
/*!40000 ALTER TABLE `user_has_watchlist_products` DISABLE KEYS */;
INSERT INTO `user_has_watchlist_products` VALUES (1,1),(2,1),(1,2),(1,4),(1,5),(3,6),(3,8);
/*!40000 ALTER TABLE `user_has_watchlist_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary key - arbitraty user ID',
  `is_admin` tinyint(1) NOT NULL,
  `username` varchar(45) NOT NULL COMMENT 'User''s username',
  `email` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL COMMENT 'User''s password',
  `first_name` varchar(45) NOT NULL COMMENT 'User''s first name',
  `last_name` varchar(45) NOT NULL,
  `registration_date` date NOT NULL COMMENT 'Date of user''s registration',
  `phone` varchar(45) DEFAULT NULL COMMENT 'User''s phone number',
  `last_login` timestamp NULL DEFAULT NULL COMMENT 'User''s last login time',
  `profile_picture` varchar(200) DEFAULT NULL COMMENT 'Path to user''s profile picture',
  `money` decimal(7,2) DEFAULT '150.00' COMMENT 'User''s money',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `USERNAME_UNIQUE` (`username`),
  UNIQUE KEY `EMAIL_UNIQUE` (`email`),
  KEY `USERNAME_INDEX` (`username`),
  KEY `EMAIL_INDEX` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,0,'sgekov','svetoslav_gekov@abv.bg','$2a$10$1jk0yGGk90M1Bhi6V.4iJO1fcqI77czYaCj9esf0DEAZM7ocIH7fy','Svetoslav','Gekov','2018-04-01','0882022041','2018-05-03 07:47:48','Picture_Svetoslav_Gekov.jpg',9838.13),(2,0,'mdimitrov','mario0.bg@abv.bg','$2a$10$0/PmI5dkHCtW.7T/wQbSr.AogH65p4fSaPpGC8TmhFAoaIbHnf6um','Mario','Dimitrov','2018-04-01',NULL,'2018-04-29 13:51:26',NULL,200.00),(3,1,'admin','admin@filmoteka.bg','$2a$10$BhejaMOwKu8AOxnK/M0d3OxF6dtgnTqr2fMdck/2rW/C3o1w7jJwm','Admin','Adminov','2018-04-01','0889853350','2018-05-05 09:34:43',NULL,4987.15);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-05 13:04:52
