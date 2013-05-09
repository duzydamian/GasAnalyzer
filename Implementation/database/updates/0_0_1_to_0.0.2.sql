------------------------------
-- Update 0.0.1 to 0.0.2
--
-- Project    : GasAnalyzer
-- Description: Change load(survey) column type to String
------------------------------
ALTER TABLE survey 
    ALTER COLUMN load TYPE character varying(100);
