CREATE PROCEDURE CopyMissingRecordsWithFieldComparison (
    @SourceDatabase NVARCHAR(128),
    @DestinationDatabase NVARCHAR(128)
)
    AS
BEGIN
    DECLARE @TableName NVARCHAR(128)
    DECLARE @SQL NVARCHAR(MAX)

    -- Cursor to iterate through tables
    DECLARE TableCursor CURSOR FOR
SELECT TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_CATALOG = @SourceDatabase

    OPEN TableCursor
    FETCH NEXT FROM TableCursor INTO @TableName

    -- Loop through tables
    WHILE @@FETCH_STATUS = 0
BEGIN

        -- Print the dynamic SQL statement
        PRINT @sql;

        -- Generate dynamic SQL statement with field comparison
        SET @SQL = 'INSERT INTO ' + QUOTENAME(@DestinationDatabase) + '.dbo.' + QUOTENAME(@TableName) +
                   ' SELECT * FROM ' + QUOTENAME(@SourceDatabase) + '.dbo.' + QUOTENAME(@TableName) +
                   ' WHERE NOT EXISTS (SELECT * FROM ' + QUOTENAME(@DestinationDatabase) + '.dbo.' + QUOTENAME(@TableName) +
                   ' WHERE '

        -- Get column list for field comparison
        DECLARE @ColumnList NVARCHAR(MAX)
        SET @ColumnList = ''

        -- Cursor to iterate through columns
        DECLARE ColumnCursor CURSOR FOR
SELECT COLUMN_NAME
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = @TableName

    OPEN ColumnCursor
DECLARE @ColumnName NVARCHAR(128)
        DECLARE @DataType NVARCHAR(128)

        FETCH NEXT FROM ColumnCursor INTO @ColumnName

        -- Loop through columns
        WHILE @@FETCH_STATUS = 0
BEGIN
            -- Get the data type of the column
SELECT @DataType = DATA_TYPE
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = @TableName AND COLUMN_NAME = @ColumnName

  -- Check if the data type is 'text'
    IF @DataType IN ('text', 'image')
BEGIN
                -- Skip comparison for 'text' data type
FETCH NEXT FROM ColumnCursor INTO @ColumnName
    CONTINUE
END

            -- Append field comparison for each non-'text' column
            SET @ColumnList = @ColumnList + QUOTENAME(@SourceDatabase) + '.dbo.' + QUOTENAME(@TableName) + '.' + QUOTENAME(@ColumnName) +
                              ' = ' + QUOTENAME(@DestinationDatabase) + '.dbo.' + QUOTENAME(@TableName) + '.' + QUOTENAME(@ColumnName) + ' AND '

            FETCH NEXT FROM ColumnCursor INTO @ColumnName
END

CLOSE ColumnCursor
    DEALLOCATE ColumnCursor

        -- Remove the trailing "OR" from the column list
        SET @ColumnList = LEFT(@ColumnList, LEN(@ColumnList) - 3)

        -- Append the column list to the dynamic SQL statement
        SET @SQL = @SQL + @ColumnList + ')'

-- Execute the dynamic SQL statement
SELECT @SQL
    EXEC sp_executesql @SQL

        FETCH NEXT FROM TableCursor INTO @TableName
END

CLOSE TableCursor
    DEALLOCATE TableCursor
END


EXEC CopyMissingRecordsWithFieldComparison 'Adeptia_Connect_Old', 'Adeptia_Connect_Clean';

Drop PROCEDURE dbo.CopyMissingRecordsWithFieldComparison;