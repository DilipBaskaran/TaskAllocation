
/*PRIORITY TABLE DEFAULT ENTRIES*/
insert into taskallocationdb.priority(id,priority_key,priority_value,sort_order) values(11,'CRITICAL','Critical',1);
insert into taskallocationdb.priority(id,priority_key,priority_value,sort_order) values(12,'HIGH','High',2);
insert into taskallocationdb.priority(id,priority_key,priority_value,sort_order) values(13,'MEDIUM','Medium',3);
insert into taskallocationdb.priority(id,priority_key,priority_value,sort_order) values(14,'LOW','Low',4);



/*TASKSTATUS TABLE DEFAULT ENTRIES*/
insert into taskallocationdb.task_status(id,status_key,status_value,sort_order) values(15,'OPEN','Open',1);
insert into taskallocationdb.task_status(id,status_key,status_value,sort_order) values(16,'INPROGRESS','In Progress',2);
insert into taskallocationdb.task_status(id,status_key,status_value,sort_order) values(17,'COMPLETED','Completed',3);
/*insert into priority(status_key,status_value,sort_order) values('OPEN','Open',1);
insert into priority(status_key,status_value,sort_order) values('OPEN','Open',1);*/