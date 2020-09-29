CREATE SCHEMA `mix` DEFAULT CHARACTER SET utf8;

CREATE TABLE `mix`.`product` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DOUBLE NOT NULL,
  `is_deleted` TINYINT(1)  NOT NULL DEFAULT 0,
  PRIMARY KEY (`product_id`));

  CREATE TABLE `mix`.`users` (
  `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `login` VARCHAR(256) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `is_deleted` TINYINT(1)  NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`));

  CREATE TABLE `mix`.`roles` (
  `role_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  `is_deleted` TINYINT(1)  NOT NULL DEFAULT 0,
  PRIMARY KEY (`role_id`));

CREATE TABLE `mix`.`users_roles` (
  `user_id` BIGINT(11) NOT NULL,
  `role_id` BIGINT(11) NOT NULL DEFAULT 2,
  INDEX `fk_users_roles_1_idx` (`user_id` ASC),
  INDEX `fk_users_roles_2_idx` (`role_id` ASC),
  CONSTRAINT `fk_users_roles_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mix`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_roles_2`
    FOREIGN KEY (`role_id`)
    REFERENCES `mix`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

  CREATE TABLE `mix`.`shopping_carts` (
  `cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `is_deleted` TINYINT(1)  NOT NULL DEFAULT 0,
  PRIMARY KEY (`cart_id`),
  INDEX `fk_shopping_carts_1_idx` (`user_id` ASC),
  CONSTRAINT `fk_shopping_carts_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mix`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

  CREATE TABLE `mix`.`shopping_carts_products` (
  `cart_id` BIGINT(11) NOT NULL,
  `product_id` BIGINT(11) NOT NULL,
  INDEX `fk_shopping_carts_products_1_idx` (`cart_id` ASC),
  INDEX `fk_shopping_carts_products_2_idx` (`product_id` ASC),
  CONSTRAINT `fk_shopping_carts_products_1`
    FOREIGN KEY (`cart_id`)
    REFERENCES `mix`.`shopping_carts` (`cart_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shopping_carts_products_2`
    FOREIGN KEY (`product_id`)
    REFERENCES `mix`.`product` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

  CREATE TABLE `mix`.`orders` (
  `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `is_deleted` TINYINT(1)  NOT NULL DEFAULT 0,
  PRIMARY KEY (`order_id`),
  INDEX `fk_orders_1_idx` (`user_id` ASC),
  CONSTRAINT `fk_orders_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mix`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

  CREATE TABLE `mix`.`orders_products` (
  `order_id` BIGINT(11) NOT NULL,
  `product_id` BIGINT(11) NOT NULL,
  INDEX `fk_orders_products_1_idx` (`order_id` ASC),
  INDEX `fk_orders_products_2_idx` (`product_id` ASC),
  CONSTRAINT `fk_orders_products_1`
    FOREIGN KEY (`order_id`)
    REFERENCES `mix`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_products_2`
    FOREIGN KEY (`product_id`)
    REFERENCES `mix`.`product` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `mix`.`product` (`name`, `price`) VALUES ('milk', '22.22');
INSERT INTO `mix`.`product` (`name`, `price`) VALUES ('cookie', '33.33');
INSERT INTO `mix`.`product` (`name`, `price`) VALUES ('water', '11.11');
INSERT INTO `mix`.`roles` (`role_name`) VALUES ('admin');
INSERT INTO `mix`.`roles` (`role_name`) VALUES ('user');
INSERT INTO `mix`.`users` (`name`, `login`, `password`) VALUES ('admin', 'admin', '1234');
INSERT INTO `mix`.`users` (`name`, `login`, `password`) VALUES ('user', 'user', '4321');
INSERT INTO `mix`.`users_roles` (`user_id`, `role_id`) VALUES ('1', '1');
INSERT INTO `mix`.`users_roles` (`user_id`, `role_id`) VALUES ('1', '2');
INSERT INTO `mix`.`users_roles` (`user_id`, `role_id`) VALUES ('2', '2');
INSERT INTO `mix`.`shopping_carts` (`cart_id`, `user_id`) VALUES ('1', '1');
INSERT INTO `mix`.`shopping_carts` (`cart_id`, `user_id`) VALUES ('2', '2');