CREATE OR ALTER PROCEDURE GetProjects
AS
SELECT project_ID, project_Name, (select count(*)
    from TestRun b
    where a.project_ID = b.project_id) active_test_run,
    (select count(*)
    from Milestones c
    where a.project_ID = c.project_id) active_milestone
FROM Projects a;
