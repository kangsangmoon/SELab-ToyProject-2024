create table user
(
    id              int auto_increment comment '사용자 고유식별'
        primary key,
    user_name       varchar(10) not null comment ' 사용자 이름',
    user_email      varchar(20) not null comment '사용자 이메일',
    user_password   varchar(20) not null comment '사용자 비밀번호',
    user_created_at datetime    null comment '계정 생성 시간
',
    user_updated_at datetime    null comment '마지막 수정된 시간',
    constraint id_UNIQUE
        unique (id),
    constraint user_email_UNIQUE
        unique (user_email),
    constraint user_name_UNIQUE
        unique (user_name)
);

create table problem
(
    prob_id          int auto_increment comment '문제 고유 식별'
        primary key,
    prob_title       text     not null comment '문제 제목',
    prob_description text     not null comment '문제 설명',
    prob_in_ex       text     not null comment '문제 입력 예시',
    prob_out_ex      text     not null comment '문제 출력 예시',
    prob_difficulty  int      not null comment '문제 난이도숫자',
    created_at       datetime null comment '문제 생성 시간',
    updated_at       datetime null comment '문제 정보 수정된 시간',
    constraint prob_id_UNIQUE
        unique (prob_id)
);

create table submission
(
    sub_id       int auto_increment comment '제출 기록의 고유 식별'
        primary key,
    user_id      int                           not null comment '제출한 사용자의 id. User 테이블의 외래키',
    sub_prob_id  int                           not null comment '제출한 문제의 Prob_id.Problem 테이블의 외래키',
    sub_code     text                          not null comment '사용자 제출 코드',
    sub_result   varchar(50) default 'pending' not null comment '제출 결과 (성공 or 실패) 값 저장',
    submitted_at datetime                      null comment '제출 시간',
    constraint sub_id_UNIQUE
        unique (sub_id),
    constraint submission_ibfk_1
        foreign key (user_id) references user (id),
    constraint submission_ibfk_2
        foreign key (sub_prob_id) references problem (prob_id),
    constraint submission_ibfk_3
        foreign key (user_id) references user (id),
    constraint submission_ibfk_4
        foreign key (sub_prob_id) references problem (prob_id)
);

create index idx_submission_sub_prob_id
    on submission (sub_prob_id);

create index idx_submission_user_id
    on submission (user_id);

create table ranking
(
    rank_id          int           not null,
    rank_total_score int default 0 not null comment '사용자 총점수',
    ranknum          int default 1 not null comment '사용자 랭킹 순위',
    user_id          int auto_increment comment ' id.User 와의 외래키'
        primary key,
    updated_at       datetime      null,
    constraint idx_ranking_user_id
        unique (user_id),
    constraint user_id_UNIQUE
        unique (user_id),
    constraint ranking_ibfk_1
        foreign key (user_id) references user (id),
    constraint ranking_ibfk_2
        foreign key (user_id) references user (id),
    constraint ranking_ibfk_3
        foreign key (user_id) references user (id),
    constraint ranking_ibfk_4
        foreign key (user_id) references user (id)
);

create index idx_ranking_rank_total_score
    on ranking (rank_total_score);

create index idx_ranking_ranknum
    on ranking (ranknum);
