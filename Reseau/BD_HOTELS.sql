SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `bd_hotels` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `bd_hotels` ;

-- -----------------------------------------------------
-- Table `mydb`.`voyageurs`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd_hotels`.`voyageurs` (
  `id` INT(4) NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(255) NOT NULL ,
  `prenom` VARCHAR(255) NOT NULL ,
  `adresse_domicile` VARCHAR(255) NULL ,
  `password` VARCHAR(255) NOT NULL ,
  `email` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `mydb`.`voyageurs-accompagnants`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd_hotels`.`voyageurs-accompagnants` (
  `id` INT(4) NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(255) NOT NULL ,
  `prenom` VARCHAR(255) NOT NULL ,
  `adresse_domicile` VARCHAR(255) NULL ,
  `titulaire` INT(4) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_voyageurs-accompagnants_voyageurs_idx` (`titulaire` ASC) )
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `mydb`.`chambres`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd_hotels`.`chambres` (
  `numero` INT(5) NOT NULL ,
  `douche` TINYINT(1) NULL DEFAULT 1 ,
  `baignoire` TINYINT(1) NULL DEFAULT 1 ,
  `cuvette` TINYINT(1) NULL DEFAULT 1 ,
  `nb_occupants` INT(4) NULL ,
  `prix_htva` INT(6) NULL ,
  PRIMARY KEY (`numero`) )
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `mydb`.`reservations`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd_hotels`.`reservations` (
  `id` VARCHAR(255) NOT NULL,
  `chambre` INT(5) NULL ,
  `payé` TINYINT(1) NULL DEFAULT 0 ,
  `titulaire` INT(4) NULL ,
  `date_reservation` VARCHAR(255) NULL ,
  `heure_reservation` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_reservations_voyageurs1_idx` (`titulaire` ASC) ,
  INDEX `fk_reservations_chambres1_idx` (`chambre` ASC) )
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `mydb`.`activites`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd_hotels`.`activites` (
  `id` INT(4) NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(255) NULL ,
  `nb_max` INT(4) NULL ,
  `prix_htva` INT(6) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
