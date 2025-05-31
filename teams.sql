-- Create or replace the 'teams' table
create or replace table teams (
    -- The unique identifier for each team (primary key)
    teamId int primary key,
    -- The city where the team is based, limited to 30 characters
    city varchar(30),
    -- The name of the team, limited to 30 characters
    name varchar(30)
);

-- Insert initial data into the 'teams' table
insert into teams
values
    -- Inserting team data: teamId, city, and team name
    (1, "Pittsburgh", "Pirates"),    -- Team 1: Pirates from Pittsburgh
    (2, "New York", "Mets"),         -- Team 2: Mets from New York
    (3, "Philadelphia", "Phillies"); -- Team 3: Phillies from Philadelphia
