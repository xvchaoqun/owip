
drop trigger if exists trig_cet_project_plan;
CREATE TRIGGER `trig_cet_project_plan` AFTER UPDATE
ON `cet_project_plan` FOR EACH ROW update cet_project set has_archive=0 where id = NEW.project_id;


drop trigger if exists trig_cet_project_obj;
CREATE TRIGGER `trig_cet_project_obj` AFTER UPDATE
ON `cet_project_obj` FOR EACH ROW update cet_project set has_archive=0 where id = NEW.project_id;


drop trigger if exists trig_cet_train;
CREATE TRIGGER `trig_cet_train` AFTER UPDATE
ON `cet_train` FOR EACH ROW update cet_project p, cet_project_plan pp set p.has_archive=0 where p.id = pp.project_id and pp.id=NEW.plan_id;


drop trigger if exists trig_cet_train_obj;
CREATE TRIGGER `trig_cet_train_obj` AFTER UPDATE
ON `cet_train_obj` FOR EACH ROW update cet_project p, cet_project_obj po set p.has_archive=0 where p.id = po.project_id and po.id=NEW.obj_id;


drop trigger if exists trig_cet_train_course;
CREATE TRIGGER `trig_cet_train_course` AFTER UPDATE
ON `cet_train_course` FOR EACH ROW update cet_project p, cet_project_plan pp, cet_train t set p.has_archive=0 where p.id = pp.project_id and pp.id=t.plan_id and t.id=NEW.train_id;

