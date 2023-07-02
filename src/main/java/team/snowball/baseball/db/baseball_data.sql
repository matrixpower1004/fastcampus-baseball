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
insert into out_player(player_id, name, reason, created_at) values (10, "은퇴", now());
insert into out_player(player_id, name, reason, created_at) values (11, "은퇴", now());


-- Dynamic pivot query
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


-- JDBC에서 프로시저 호출로 실행
CALL POSITION_PIVOT();


