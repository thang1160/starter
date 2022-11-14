CREATE PROCEDURE [dbo].[GetTestRunsByProjectId]
    @project_id int
AS
SELECT run_ID, run_Name, FORMAT (created_on, 'dd/MM/yyyy'), b.fullname, passed_count, retest_count, failed_count, untested_count
from TestRun a join Users b on a.user_id = b.user_ID
where project_id = @project_id;
GO
