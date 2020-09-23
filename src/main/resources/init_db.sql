CREATE SCHEMA `mix_shop` DEFAULT CHARACTER SET utf8;

CREATE TABLE `mix`.`product` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DOUBLE NOT NULL,
  `is_deleted` VARCHAR(45) NOT NULL DEFAULT 0,
  PRIMARY KEY (`product_id`));

INSERT INTO `mix`.`product` (`name`, `price`) VALUES ('milk', '22.22');
INSERT INTO `mix`.`product` (`name`, `price`) VALUES ('cookie', '33.33');
INSERT INTO `mix`.`product` (`name`, `price`) VALUES ('water', '11.11');
