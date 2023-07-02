# Fastcampus-baseball
![다운로드](https://github.com/Horaiz-UQ/fastcampus-baseball/assets/116620881/de8c4944-6c46-4cf3-b5cd-15a5bb9449bf)


야구 관리 프로그램

## 프로젝트 시작 전 약속
* Github project를 사용하여 일정관리 및 이슈 관리
* Enum 사용하여 입력 메세지 및 출력 메세지 관리
* 추상화 사용하기

## 프로젝트 기술 소개 Technology List

### 사용 언어 및 기술스택
- Java 17
- MySQL DB
- AssertJ
- JUnit

### 개발환경
* IntelliJ IDEA
* MySQL

### DB Table
```sql
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
    reason varchar(255) not null,
    created_at timestamp not null,
    
    foreign key (player_id) references player(id)
);
```
### Dummy data
```sql
-- 야구장 데이터 생성
insert into stadium(name, created_at) values("고척스카이돔", now());
insert into stadium(name, created_at) values("창원NC파크", now());
insert into stadium(name, created_at) values("서울종합운동장야구장", now());

-- team 데이터 생성
insert into team(stadium_id, name, created_at) values(1, "키움", now());
insert into team(stadium_id, name, created_at) values(2, "NC", now());
insert into team(stadium_id, name, created_at) values(3, "LG", now());

-- player 데이터 생성
insert into player(team_id, name, position, created_at) values(2, "박민우", "2루수", now());
insert into player(team_id, name, position, created_at) values(2, "이재학", "투수", now());
insert into player(team_id, name, position, created_at) values(2, "박석민", "3루수", now());
insert into player(team_id, name, position, created_at) values(1, "이용규", "좌익수", now());
insert into player(team_id, name, position, created_at) values(1, "이정후", "중견수", now());
insert into player(team_id, name, position, created_at) values(1, "이지영", "포수", now());
insert into player(team_id, name, position, created_at) values(3, "박동원", "포수", now());
insert into player(team_id, name, position, created_at) values(3, "오지환", "유격수", now());
insert into player(team_id, name, position, created_at) values(3, "김현수", "좌익수", now());
insert into player(team_id, name, position, created_at) values(null, "이상훈", "투수", now());
insert into player(team_id, name, position, created_at) values(null, "모창민", "유격수", now());


-- 은퇴 선수 데이터 생성
insert into out_player(player_id, reason, created_at) values (10, "은퇴", now());
insert into out_player(player_id, reason, created_at) values (11, "은퇴", now());
```
### ERD
![Untitled](https://github.com/Horaiz-UQ/fastcampus-baseball/assets/116620881/7009aa58-99f9-4b33-a992-bbc36a1d5301)


### 클래스 다이어 그램
#### \<Model>
* model
* dao
* handler
* dto
* db

#### \<Controller>
* code
* service
* controller

#### \<View> 
* View

### 팀원 역할 분담
* 팀장 : [이지상](https://github.com/matrixpower1004) Jisang Lee
    * 프로젝트 Set-up
    * DB Connection 및 테이블 작성
    * 공통 입력/파라미터 구현
    * 공통 명령어 처리 Controller 구현
    * Player/OutPlayer  구현
    * 통합 테스트
* 팀원 : [김용원](https://github.com/Horaiz-UQ) Yongwon Kim
    * Stadium 구현
    * Team 구현
    * 발표 준비 및 문서 작성

## 프로젝트 요약

### Github Project 사용
<img src="./https://github.com/Horaiz-UQ/fastcampus-baseball/assets/116620881/08c95fc5-25a3-4090-828f-4c9ca1a8c79f" width="800">

* project를 사용하여 일정 관리 및 이슈 관리 용이 
* Main 브런치 사용

### Enum과 functional interface 활용
<img src="./https://github.com/Horaiz-UQ/fastcampus-baseball/assets/116620881/447b2b2d-efeb-4fc9-bf0f-01403fc81f12" width="500">
<img src="./https://github.com/Horaiz-UQ/fastcampus-baseball/assets/116620881/8e1ccf6e-4553-46af-b0e9-c2f8abfb5a37" width="600">

* Enum과 functional interface을 사용하여 if 문 분기를 최소화하고 비즈니스 로직을 구현

### 출력 메세지 Enum을 사용
<img src="./https://github.com/Horaiz-UQ/fastcampus-baseball/assets/116620881/df983391-632e-4543-84a3-21bc10dabfa4" width="700">

* 출력 메시지를 enum을 통해 상수화 함으로써 유지보수 용이

### 추상화
<img src="./https://github.com/Horaiz-UQ/fastcampus-baseball/assets/116620881/457ce004-03c7-4876-abc4-7d84feca0bdd4" width="700">
 
* 매우 높은 추상화를 통해 협업 용이 
