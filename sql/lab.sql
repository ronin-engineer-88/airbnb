-- Geographic Coordinate
-- longitude, latitude = 21.010219168557075, 105.85054073130831

-- Convert from 4326 (angular) to 3857 (meters)
select st_transform(st_setsrid(st_makepoint(105.85054073130831, 21.010219168557075), 4326), 3857);
