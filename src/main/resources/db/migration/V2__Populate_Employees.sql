INSERT INTO employees (
    id, first_name, last_name, age, personnel_number, department, job_description, annual_income
) VALUES
      (gen_random_uuid(), 'Max', 'Mustermann', 38, 'mm1288_se', 'SOFWARE_DEVELOPMENT', 'Backend-Entwickler', 75000),
      (gen_random_uuid(), 'Miriam', 'Müller', 42, 'mm5482_pm', 'PROJECT_MANAGEMENT', 'Projektmanagerin', 83000),
      (gen_random_uuid(), 'Bertram', 'Bauer', 56, 'bb4849_se', 'SOFWARE_DEVELOPMENT', 'Frontend-Entwickler', 72000),
      (gen_random_uuid(), 'Fiona', 'Fischer', 27, 'ff4711_hr', 'HUMAN_RESOUCES', 'Recruiterin', 52000),
      (gen_random_uuid(), 'Agathe', 'Adams', 51, 'aa2376_se', 'SOFWARE_DEVELOPMENT', 'Frontend-Entwicklerin', 72000),
      (gen_random_uuid(), 'Moritz', 'Meier', 32, 'mm8523_hr', 'HUMAN_RESOUCES', 'Recruiter', 52000);