# Fastcampus-baseball
![다운로드](https://github.com/Horaiz-UQ/fastcampus-baseball/assets/116620881/de8c4944-6c46-4cf3-b5cd-15a5bb9449bf)


# 패스트캠퍼스 Toy Project II : 야구 관리 프로그램

## 프로젝트 check list
- [x] Github project를 사용하여 일정 관리 및 이슈 관리.
- [x] Enum을 활용한 공통 입출력 메시지 관리.
- [x] 추상화를 통한 협업 효율성 높이기.
- [x] 변수명과 메서드명의 Code convention 지키기.
- [x] Functional 인터페이스를 사용하여 객체의 불변성을 보장하고, thread safe한 코드를 구현하기.

## 프로젝트 기술 소개 Technology List

### 사용 언어 및 기술스택
- Java 17
- Gradle
- Lombok
- AssertJ
- JUnit

### 개발환경
* IntelliJ IDEA
* MySQL 8.0.33

### 협업 툴
* Github project
* Slack
* Zoom

### Dependencies(build.gradle)
```Groovy
plugins {
    id 'java'
    id "io.freefair.lombok" version "8.0.1"
}

group = 'team.snowball.baseball'
version = '1.0-SNAPSHOT'

java {
    sourceCompatibility = '17'
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly 'org.projectlombok:lombok:1.18.26'
    implementation 'com.mysql:mysql-connector-j:8.0.33'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.assertj:assertj-core:3.24.2'
    testImplementation platform('org.junit:junit-bom:5.9.1')
    testImplementation 'org.junit.jupiter:junit-jupiter'
}

test {
    useJUnitPlatform()
}
```

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

### Test data
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

### DB Procedure
```sql
DROP PROCEDURE IF EXISTS POSITION_PIVOT;

DELIMITER $$
CREATE PROCEDURE POSITION_PIVOT(
)
BEGIN

SET @sql = (
  SELECT GROUP_CONCAT(
    CONCAT(
      'MAX(CASE WHEN t.id = ', id, ' THEN p.name ELSE '''' END) AS `', name, '`'
    )
    ORDER BY id
  )
  FROM team
);

SET @query = CONCAT(
  'SELECT p.position, ', @sql, '
   FROM player p
   LEFT JOIN team t ON p.team_id = t.id
   GROUP BY p.position
   ORDER BY p.position'
);

PREPARE stmt FROM @query;
EXECUTE stmt;

END$$
DELIMITER ;
```
* 요구 사항 중 하나인 포지션 별 팀 야구 선수 페이지 구현을 위한 동적 Pivot query.
* JDBC 또는 DB tool 에서 `CALL POSITION_PIVOT();` 호출로 실행.

## ERD
![Untitled](https://github.com/Horaiz-UQ/fastcampus-baseball/assets/116620881/7009aa58-99f9-4b33-a992-bbc36a1d5301)


## 팀원 역할 분담
* 팀장 : [이지상](https://github.com/matrixpower1004) Jisang Lee
    * 프로젝트 Setup
    * DB Connection 및 테이블 생성
    * 공통 입력 및 파라미터를 처리하는 InputServicer 구현
    * 사용자가 입력한 명령어를 해석하여 메서드를 호출하는 Main Controller 구현
    * Player, OutPlayer  구현
    * 통합 테스트
* 팀원 : [김용원](https://github.com/Horaiz-UQ) Yongwon Kim
    * Stadium 구현
    * Team 구현
    * 통합 테스트
    * 발표 준비 및 문서 작성

## 프로젝트 요약

### Github Project 사용
![Image](https://github.com/matrixpower1004/fastcampus-baseball/assets/104916288/11d663c3-8244-41d9-9a3a-946f18a69ba0)
* Github의 project 기능을 통하여 일정 및 이슈를 관리
* Github branch 전략 사용.


### Enum을 통한 Controller 구현
![enum1](https://github.com/matrixpower1004/fastcampus-baseball/assets/104916288/8a0fdf76-edcb-44de-935f-81d2593c4d21)
* 이 프로젝트에서 가장 많은 시간과 공을 들인 부분입니다.
* 어떤 프로젝트든 기본적인 CRUD를 하는 것은 같고, 이런 공통된 부분을 추상화를 통해 해결할 수 없을까 라는 부분을 고민하였습니다.
* 사용자 입력 데이터의 명령어 및 Parameter를 parsing하면서 Enum을 통한 category화가 이루어지는데, Controller에서 또 분기를 위한 if문을 사용할 필요가 있을까? 이런 질문으로 시작된 생각은 if문으로 분기를 하는 대신 Enum의 category화 된 구조를 활용할 방법은 없을까 라는 생각으로 이어졌습니다.
* 마지막으로는 enum을 통한 method 주입을 구현해 볼 수는 없을까라는 생각에 enum을 통한 컨트롤러를 구헌하게 되었습니다.


### Enum과 functional interface를 활용한 추상화
![interface](https://github.com/matrixpower1004/fastcampus-baseball/assets/104916288/c2edbcbd-5656-4cb5-bced-5349867d4958)
* Interface를 통해서 공통적인 메서드의 통일성을 이루어 협업의 용이성을 높였습니다. 이 부분은 프로젝트 발표회 때 강사님께 많은 칭찬을 받았고, 프로젝트의 팀장으로 뿌듯함을 느낍니다. (자랑이 하고 싶었어요~!)
* Functional interface를 적극 활용함으로서 객체의 불변성을 보장하고, thread safe한 코드를 구현하였습니다.

### Enum을 사용한 공통 메시지 출력
<img src="https://github.com/Horaiz-UQ/fastcampus-baseball/assets/116620881/df983391-632e-4543-84a3-21bc10dabfa4" width="700">

* 사용자에게 보여지는 메시지를 Enum을 통해 상수화함으로써, 어느 비즈니스 로직에서나 공통된 사용자 메시지를 출력하도록 보장하였습니다.
* 또한 메시지가 바뀌는 경우에도 유지보수가 용이하도록 구현하였습니다.

## 프로젝트 후기
* 팀장 : 이지상
    * 시간이 부족하여 어노테이션을 직접 구현하여 적용해 보지 못한 점이 아쉬웠습니다.
    * Java 14부터 지원된 record 클래스를 활용해 보고자 Java 17을 선택했는데 builder 패턴에 길들여져서(?) record를 활용해 보지 못한 점이 아쉬었습니다. 다른 프로젝트엔 꼭~!(really?)
    * Functional interface를 마음껏 활용해 본 프로젝트였고, 추상화를 어디까지 구현할 수 있는제 스스로에 대한 도전적인 프로젝트였습니다.
    * GitFlow brach 전략을 사용하지 못한 점이 아쉬었습니다. 프로젝트의 커밋이 처음 생각보다 Devlop branch의 필요성이 대두되었고, 다음 프로젝트에는 꼭 Develop branch를 사용하는 GitFlow branch 전략을 사용해 보고 싶네요.
* 팀원 : 김용원
    * 
