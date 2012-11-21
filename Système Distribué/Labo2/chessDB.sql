SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `chessDB` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `chessDB` ;

-- -----------------------------------------------------
-- Table `mydb`.`Plateaux`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `chessDB`.`Plateaux` (
  `nomPlateau` VARCHAR(255) NOT NULL ,
  `nombreDeJoueurs` INT NOT NULL ,
  PRIMARY KEY (`nomPlateau`) )
ENGINE = MyISAM;

-- -----------------------------------------------------
-- Table `mydb`.`Pions`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `chessDB`.`Pions` (
  `idPion` INT NOT NULL AUTO_INCREMENT,
  `nomPion` VARCHAR(255) NOT NULL ,
  `positionX` INT NOT NULL ,
  `positionY` INT NOT NULL ,
  `nomPlateau` VARCHAR(255) NOT NULL ,
  `couleur` INT NOT NULL ,
  PRIMARY KEY (`idPion`) ,
  INDEX `fk_Pions_Plateaux_idx` (`nomPlateau` ASC) ,
  CONSTRAINT `fk_Pions_Plateaux`
    FOREIGN KEY (`nomPlateau` )
    REFERENCES `chessDB`.`Plateaux` (`nomPlateau` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM;

CREATE  TABLE IF NOT EXISTS `chessDB`.`Joueurs` (
  `idJoueur` INT NOT NULL AUTO_INCREMENT,
  `couleur` INT NOT NULL,
  `nomPlateau` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`idJoueur`) ,
  INDEX `fk_Pions_Plateaux_idx` (`nomPlateau` ASC) ,
  CONSTRAINT `fk_Joueurs_Plateaux`
    FOREIGN KEY (`nomPlateau` )
    REFERENCES `chessDB`.`Plateaux` (`nomPlateau` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
