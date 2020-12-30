desc board;

-- list
  select a.no, a.title, date_format('%Y', a.reg_date), a.hit, b.name 
    from board a, user b
   where a.user_no = b.no
order by a.group_no desc, a.order_no asc
   limit 0, 10;