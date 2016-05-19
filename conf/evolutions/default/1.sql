# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table farmer (
  id                        bigint not null,
  useremail                 varchar(255),
  password                  varchar(255),
  farm                      varchar(255),
  username                  varchar(255),
  produce                   varchar(255),
  produce_price             decimal(38),
  wepay_access_token        varchar(255),
  wepay_account_id          bigint,
  constraint pk_farmer primary key (id))
;

create sequence farmer_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists farmer;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists farmer_seq;

