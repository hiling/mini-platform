/*
DROP TABLE IF EXISTS `oauth_client`;
DROP TABLE IF EXISTS `oauth_access_token`;
DROP TABLE IF EXISTS `oauth_refresh_token`;
*/

CREATE TABLE `oauth_client` (
	`client_id` CHAR(16) NOT NULL,
	`client_name` VARCHAR(16) NOT NULL,
	`client_secret` CHAR(32) NOT NULL,
	`grant_types` VARCHAR(64) NOT NULL DEFAULT '',
	`ip_whitelist` VARCHAR(128) NOT NULL DEFAULT '',
	`redirect_uri` VARCHAR(200) NOT NULL DEFAULT '',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`client_id`)
) COLLATE='utf8_bin' ENGINE=InnoDB;

CREATE TABLE `oauth_access_token` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`client_id` CHAR(16) NOT NULL,
	`user_id` VARCHAR(16) NOT NULL DEFAULT '',
	`access_token` CHAR(32) NOT NULL,
	`jwt_token` VARCHAR(500) NOT NULL DEFAULT '',
	`refresh_token` VARCHAR(32) NOT NULL DEFAULT '',
	`expires` INT(11) NOT NULL DEFAULT 0,
	`scope` VARCHAR(200) NOT NULL DEFAULT '',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	INDEX `ix_client_id` (`client_id`),
	INDEX `ix_access_token` (`access_token`)
) COLLATE='utf8_bin' ENGINE=InnoDB;

CREATE TABLE `oauth_refresh_token` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `token_id` BIGINT(20) NOT NULL,
  `refresh_token` CHAR(32) NOT NULL,
  `expires` INT(11) NOT NULL DEFAULT 0,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	INDEX `ix_refresh_token` (`refresh_token`)
) COLLATE='utf8_bin' ENGINE=InnoDB;