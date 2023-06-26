create database baseball;
use baseball;

create table stadium(
	id int primary key auto_increment,
    name varchar(50) not null,
    created_at timestamp not null
);

create table team (
	id int primary key auto_increment,
    stadium_id int unique not null,
    name varchar(50) not null,
    created_at timestamp not null,
    foreign key (stadium_id) references stadium(id)
);

create table player(
	id int primary key auto_increment,
    team_id int, 
    name varchar(255) not null,
    position varchar(20) not null,
    created_at timestamp not null,
    
    constraint uc_team_position unique(team_id, position),
    foreign key (team_id) references team(id)
);

create table out_player(
	id int primary key auto_increment,
    player_id int unique not null,
    name varchar(255) not null,
    reason varchar(255) not null,
    created_at timestamp not null,
    
    foreign key (player_id) references player(id)
);
