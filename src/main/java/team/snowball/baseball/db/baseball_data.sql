-- 야구장 데이터 생성
insert into stadium(name, created_at) values("고척 스카이돔", now());
insert into stadium(name, created_at) values("창원 NC파크", now());
insert into stadium(name, created_at) values("잠실 야구장", now());

-- team 데이터 생성
insert into team(stadium_id, name, created_at) values(1, "서울 키움 히어로즈", now());
insert into team(stadium_id, name, created_at) values(2, "창원 NC 다이노스", now());
insert into team(stadium_id, name, created_at) values(3, "서울 LG 트윈스", now());

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

select * from stadium;
select * from team;
select * from player;
select * from out_player;

-- 포지션 별 선수 목록 보기
SELECT
    p.position AS 포지션,
    MAX(CASE WHEN t.name = '서울 키움 히어로즈' THEN p.name ELSE NULL END) AS 키움,
    MAX(CASE WHEN t.name = '창원 NC 다이노스' THEN p.name ELSE NULL END) AS NC,
    MAX(CASE WHEN t.name = '서울 LG 트윈스' THEN p.name ELSE NULL END) AS LG
FROM
    team t
        LEFT JOIN
    player p ON t.id = p.team_id
WHERE
        p.position IN ('2루수', '3루수')
GROUP BY
    p.position;

