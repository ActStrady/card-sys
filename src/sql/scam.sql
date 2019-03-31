drop database if exists card_sys;
create database card_sys;

-- 创建充值表
drop table if exists card_sys.card;
create table card_sys.card
(
    id         int primary key auto_increment,
    s_id        varchar(255) not null unique comment '学号',
    name       varchar(255) not null comment '姓名',
    password   varchar(255) not null comment '密码',
    class_name varchar(255) comment '班级名称',
    money      decimal(5, 2) comment '余额',
    balance     decimal(5, 2) comment '银行卡余额'
);

insert into card_sys.card value (null, '1850412601', '王强', '147258', '14班', 14.5, 120.5);
insert into card_sys.card value (null, '1850412602', '刘振', '147258', '14班', 114.5, 1200.5);
insert into card_sys.card value (null, '1850412603', '李小涵', '147258', '15班', 20.5, 300.5);
insert into card_sys.card value (null, '1850512601', '何宁', '147258', '16班', 10.5, 190.56);