create database if NOT exists SMS;
use SMS;

drop view if exists SALELIST;
drop table if exists GSALES;
drop table if exists GOODS;
drop table if exists SALESMAN;

create table GOODS
(
    GID    INT(10)        NOT NULL auto_increment,
    GNAME  varchar(20)    NOT NULL unique,
    GPRICE decimal(18, 1) NOT NULL,
    GNUM   INT(11)        NOT NULL,
    primary key (GID)
) engine = InnoDB
  charset = utf8;

create table SALESMAN
(
    SID       INT(10)     NOT NULL auto_increment,
    SPASSWORD varchar(20) NOT NULL,
    SNAME     varchar(10) NOT NULL unique,
    primary key (SID)
) engine = InnoDB
  charset = utf8;

create table GSALES
(
    GSID  INT(10) NOT NULL auto_increment,
    GID   INT(10) NOT NULL,
    SID   INT(10) NOT NULL,
    SDATE date    NOT NULL,
    SNUM  INT(11) NOT NULL,
    primary key (GSID),
    constraint fk_gid foreign key (GID) references GOODS (GID),
    constraint fk_sid foreign key (SID) references SALESMAN (SID)
) engine = InnoDB
  charset = utf8;

CREATE VIEW SALELIST
AS
SELECT GNAME, GPRICE, GNUM, sum(SNUM) AS TOTAL, SDATE
FROM GOODS,
     SALESMAN,
     GSALES
where GOODS.GID = GSALES.GID
  AND SALESMAN.SID = GSALES.SID
group by SDATE, GNAME;