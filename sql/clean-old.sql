set names utf8;

use spider;

set @before_day=5;

select now(),'begin search process...';
drop table if exists delete_process;
create table delete_process
select id from process
where begin_time < date_sub(curdate(), interval @before_day day);
select now(),'end search process...';

select now(), 'begin search task...';
drop table if exists delete_task;
create table delete_task
select id from task
where process_id in (select id from delete_process);
select now(), 'end search task...';

select now(), 'begin search task_push...';
drop table if exists delete_task_push;
create table delete_task_push
select id from task_push
where task_id in (select id from delete_task);
select now(), 'end search task_push...';

select now(), 'begin delete task_push...';
-- delete task_push from task_push,delete_task_push where task_push.id = delete_task_push.id;
delete from task_push where id in (select id from delete_task_push);
select now(), 'end delete task_push...';

select now(), 'begin delete task...';
-- delete task from task,delete_task where task.id = delete_task.id;
delete from task where id in (select id from delete_task);
select now(), 'end delete task...';

select now(), 'begin delete process...';
-- delete process from process,delete_process where process.id = delete_process.id;
delete from process where id in (select id from delete_process);
select now(), 'end delete process...';



truncate table task_push;
truncate table task;
truncate table process;


