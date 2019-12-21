create database etoakcup;


use etoakcup;

drop table if exists `subject`;
create table subject(
	`id` varchar(50) not null,
    -- `type` int default 0 comment '题目类型 0-单选题 1-多选题 2-其他',
    `language` varchar(50) default 0 comment '题目语言 eg. Java / Pyhon',
    `question` text,
    `code` text comment '问题中的代码',
    `options` text,
    `answer` varchar(50) default '',
    `level` int default 0 comment '难度级别',
    `status` int default 0,
	PRIMARY KEY (`id`),
    INDEX (`language`),
    INDEX (`level`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT = '题目表';

-- 1.60 2.60 3,4.200 5.60
ALTER TABLE `subject` ADD INDEX ( `level`);
select count(id) from subject;
select * from subject;

drop table if exists `user`;
create table user(
	`id` varchar(50) not null,
    `authority` int default 0 comment '0-队员 1-队长 2-评委',
    `mobile` varchar(50) not null,
    `tid` varchar(50) default '1111',
    `password` varchar(50) not null,
    `item` varchar(50) default '团队赛' comment '参赛项目 java/c/php',
    `sscore` int  default 0,
	`name` varchar(50) not null,
    `major` varchar(50) not null,
    `remark` varchar(50) default '',
    primary key (`id`),
    unique index (`mobile`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT = '用户报名表';


drop table if exists `team`;
create table team(
	`id` varchar(50) not null,
    `tname` varchar(50) not null,
    `leaderid` varchar(50) not null,
    `slogan` varchar(50) not null,
    `titem` int not null comment '团队项目',
    `tscore` float default 0.0,
    primary key (`id`),
    unique index (`tname`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT = '团队表';


drop table if exists `team_subject`;
create table team_subject(
	`id` int auto_increment,
    `title` varchar(50) not null,
    `content` text,
    primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT = '比赛项目表';



insert into team (id, tname, leaderid, slogan, titem) values ('1111', '孤狼（仅参加个人赛）', '王一悦', '我是孤狼', 0);
insert into team_subject (title,content) value ('QQ(Test)' , '做一个qq');
insert into team_subject (title,content) value ('WeChet(Test)' , '做一个WeChat');
insert into team_subject (title,content) value ('Tim(Test)' , '做一个Tim');


select * from team;
select * from user;
select id
        from subject;

select * from user where tid in ( select tid from user where mobile = '17806228320');


delete from `subject` where `question` in (
		select question from (
        select question from subject group by question having count(question)>1) as tmp1)
and id not in (
		select id from (
        select min(id) from subject group by question having count(question)>1) as tmp2);