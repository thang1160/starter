CREATE PROCEDURE [dbo].[GetTestCasesByProjectId]
    @project_id int
AS
SELECT a.case_ID, a.case_Name, a.created_on, b.fullname, a.[status_id]
from TestCase a join Users b on a.user_id = b.user_ID
-- where project = project_id
;
GO
