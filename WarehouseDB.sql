CREATE DATABASE `warehouse` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `manufacturers` (
  `idManufacturer` int NOT NULL AUTO_INCREMENT,
  `manufacturer` varchar(45) NOT NULL,
  PRIMARY KEY (`idManufacturer`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `models` (
  `idModel` int NOT NULL AUTO_INCREMENT,
  `model` varchar(45) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`idModel`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `products` (
  `idproduct` int NOT NULL AUTO_INCREMENT,
  `manufacturer_product` int NOT NULL,
  `type_product` int NOT NULL,
  `model_product` int NOT NULL,
  `quantity_product` int NOT NULL,
  `storage_product` int NOT NULL,
  PRIMARY KEY (`idproduct`),
  KEY `products_models_FK` (`model_product`),
  KEY `products_manufacturers_FK` (`manufacturer_product`),
  KEY `products_types_FK` (`type_product`),
  KEY `products_storages_FK` (`storage_product`),
  CONSTRAINT `products_manufacturers_FK` FOREIGN KEY (`manufacturer_product`) REFERENCES `manufacturers` (`idManufacturer`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `products_models_FK` FOREIGN KEY (`model_product`) REFERENCES `models` (`idModel`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `products_storages_FK` FOREIGN KEY (`storage_product`) REFERENCES `storages` (`idStorage`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `products_types_FK` FOREIGN KEY (`type_product`) REFERENCES `types` (`idType`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `statususer` (
  `idstatus` int NOT NULL,
  `statusName` varchar(45) NOT NULL,
  PRIMARY KEY (`idstatus`),
  UNIQUE KEY `idstatus_UNIQUE` (`idstatus`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `storages` (
  `idStorage` int NOT NULL AUTO_INCREMENT,
  `storage` varchar(45) NOT NULL,
  `warehouseStorage` int NOT NULL,
  PRIMARY KEY (`idStorage`),
  KEY `storages_warehouses_FK` (`warehouseStorage`),
  CONSTRAINT `storages_warehouses_FK` FOREIGN KEY (`warehouseStorage`) REFERENCES `warehouses` (`idWarehouse`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `tasks` (
  `idTask` int NOT NULL AUTO_INCREMENT,
  `task` varchar(150) NOT NULL,
  PRIMARY KEY (`idTask`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `types` (
  `idType` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`idType`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `users` (
  `idusers` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `statusUser` int NOT NULL,
  PRIMARY KEY (`idusers`),
  KEY `users_statususer_FK` (`statusUser`),
  CONSTRAINT `users_statususer_FK` FOREIGN KEY (`statusUser`) REFERENCES `statususer` (`idstatus`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `warehouses` (
  `idWarehouse` int NOT NULL AUTO_INCREMENT,
  `warehouse` varchar(45) NOT NULL,
  PRIMARY KEY (`idWarehouse`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
