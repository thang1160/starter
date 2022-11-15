CREATE OR ALTER PROCEDURE [dbo].[GetTestPlansByProjectId]
    @project_id int
AS
SELECT plan_id, plan_name, description, project_id, milestone_id
from TestPlan a join Users b on a.user_id = b.user_ID
where project_id = @project_id;
GO