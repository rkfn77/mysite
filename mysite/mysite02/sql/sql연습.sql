desc user;
-- 전체회원 보기: select
select *
  from user;

-- 회원가입: insert
 insert
   into user
 values (null, '안대혁', 'kickscar@gmail.com', '1234', 'male', now());

-- 로그인: select
select no, name
  from user
 where email='kickscar@gmail.com'
   and password='1234';

-- 회원정보 보기 및 수정
-- 1. 보기: select
select no, name, email, gender, date_format(join_date, '%Y-%m-%d')
  from user
 where no=1;
-- 2. 수정: update
-- update user
--    set  
--  where no=1
