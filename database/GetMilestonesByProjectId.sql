CREATE OR ALTER PROCEDURE [dbo].[GetMilestonesByProjectId]
    @project_id int
AS
SELECT milestone_ID, milestone_Name, FORMAT (due_on, 'dd/MM/yyyy'), [status]
from Milestones
where project_id = @project_id;
GO
