USE studentms;

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE sct;
TRUNCATE TABLE ct;
TRUNCATE TABLE s;
TRUNCATE TABLE c;
TRUNCATE TABLE t;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO s (sid, sname, password) VALUES
(1, 'Emma Johnson', 's123456'),
(2, 'Liam Smith', 's123456'),
(3, 'Olivia Brown', 's123456'),
(4, 'Noah Williams', 's123456'),
(5, 'Ava Jones', 's123456'),
(6, 'William Miller', 's123456'),
(7, 'Sophia Davis', 's123456'),
(8, 'James Wilson', 's123456'),
(9, 'Isabella Moore', 's123456'),
(10, 'Benjamin Taylor', 's123456'),
(11, 'Mia Anderson', 's123456'),
(12, 'Lucas Thomas', 's123456');

INSERT INTO c (cid, cname, ccredit) VALUES
(1, 'Java Programming', 4),
(2, 'Web Application', 4),
(3, 'Database Systems', 3),
(4, 'Computer Networks', 3),
(5, 'Operating Systems', 4),
(6, 'Software Engineering', 3),
(7, 'Data Structures', 4),
(8, 'Machine Learning', 3),
(9, 'Information Security', 3),
(10, 'Cloud Computing', 2),
(11, 'Mobile Development', 3),
(12, 'Project Practice', 2);

INSERT INTO t (tid, tname, password) VALUES
(1, 'Michael Brown', 't123456'),
(2, 'Sarah Wilson', 't123456'),
(3, 'David Miller', 't123456'),
(4, 'Jennifer Davis', 't123456'),
(5, 'Robert Anderson', 't123456'),
(6, 'Emily Thompson', 't123456'),
(7, 'Daniel Clark', 't123456'),
(8, 'Laura Martinez', 't123456'),
(9, 'Matthew Lewis', 't123456'),
(10, 'Elizabeth Hall', 't123456'),
(11, 'Kevin Young', 't123456'),
(12, 'Nancy King', 't123456');

INSERT INTO ct (ctid, cid, tid, term) VALUES
(1, 1, 1, '202601'),
(2, 2, 2, '202601'),
(3, 3, 3, '202601'),
(4, 4, 4, '202601'),
(5, 5, 5, '202601'),
(6, 6, 6, '202601'),
(7, 7, 7, '202601'),
(8, 8, 8, '202601'),
(9, 9, 9, '202601'),
(10, 10, 10, '202601'),
(11, 11, 11, '202601'),
(12, 12, 12, '202601'),
(13, 1, 2, '202601'),
(14, 3, 8, '202601'),
(15, 6, 4, '202601'),
(16, 9, 5, '202601');

INSERT INTO sct (sctid, sid, cid, tid, grade, term) VALUES
(1, 1, 1, 1, 92, '202601'),
(2, 1, 2, 2, 88, '202601'),
(3, 1, 3, 3, 95, '202601'),
(4, 2, 1, 2, 84, '202601'),
(5, 2, 4, 4, 79, '202601'),
(6, 2, 5, 5, 86, '202601'),
(7, 3, 2, 2, 91, '202601'),
(8, 3, 6, 6, 83, '202601'),
(9, 3, 7, 7, 89, '202601'),
(10, 4, 3, 8, 93, '202601'),
(11, 4, 8, 8, 87, '202601'),
(12, 4, 9, 9, 82, '202601'),
(13, 5, 4, 4, 90, '202601'),
(14, 5, 10, 10, 85, '202601'),
(15, 5, 11, 11, 78, '202601'),
(16, 6, 5, 5, 94, '202601'),
(17, 6, 12, 12, 88, '202601'),
(18, 6, 1, 1, 81, '202601'),
(19, 7, 6, 4, 76, '202601'),
(20, 7, 7, 7, 92, '202601'),
(21, 7, 8, 8, 86, '202601'),
(22, 8, 9, 5, 89, '202601'),
(23, 8, 10, 10, 77, '202601'),
(24, 8, 2, 2, 83, '202601'),
(25, 9, 11, 11, 91, '202601'),
(26, 9, 12, 12, 80, '202601'),
(27, 9, 3, 3, 87, '202601'),
(28, 10, 1, 2, 88, '202601'),
(29, 10, 6, 6, 93, '202601'),
(30, 10, 9, 9, 84, '202601'),
(31, 11, 3, 8, 90, '202601'),
(32, 11, 4, 4, 82, '202601'),
(33, 11, 12, 12, 86, '202601'),
(34, 12, 5, 5, 79, '202601'),
(35, 12, 6, 4, 92, '202601'),
(36, 12, 8, 8, 88, '202601');
