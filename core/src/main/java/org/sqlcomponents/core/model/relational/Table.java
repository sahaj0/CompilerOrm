package org.sqlcomponents.core.model.relational;

import org.sqlcomponents.core.model.relational.enums.Flag;
import org.sqlcomponents.core.model.relational.enums.TableType;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * The type representing a table in a database.
 */
public class Table {
    /**
     * The Database.
     */
    private final Database database;
    /**
     * The Table name.
     */
    private String tableName;
    /**
     * The Sequence name.
     */
    private String sequenceName;
    /**
     * The Category name.
     */
    private String categoryName;
    /**
     * The Schema name.
     */
    private String schemaName;
    /**
     * The Table type.
     */
    private TableType tableType;
    /**
     * The Remarks.
     */
    private String remarks;
    /**
     * The Category type.
     */
    private String categoryType;
    /**
     * The Schema type.
     */
    private String schemaType;
    /**
     * The Name type.
     */
    private String nameType;
    /**
     * The Self referencing column name.
     */
    private String selfReferencingColumnName;
    /**
     * The Reference generation.
     */
    private String referenceGeneration;
    /**
     * The Columns.
     */
    private List<Column> columns;
    /**
     * The Indices.
     */
    private List<Index> indices;
    /**
     * The Unique columns.
     */
    private List<UniqueConstraint> uniqueColumns;

    /**
     * Gets unique columns.
     *
     * @return the unique columns
     */
    public List<UniqueConstraint> getUniqueColumns() {
        return uniqueColumns;
    }

    /**
     * Sets unique columns.
     *
     * @param paramUniqueColumns the unique columns
     */
    public void setUniqueColumns(
            final List<UniqueConstraint> paramUniqueColumns) {
        this.uniqueColumns = paramUniqueColumns;
    }

    /**
     * Instantiates a new Table.
     *
     * @param paramDatabase the database
     */
    public Table(final Database paramDatabase) {
        this.database = paramDatabase;
    }

    /**
     * Gets has primary key.
     *
     * @return whether the table has a primary key
     */
    public boolean getHasPrimaryKey() {
        return this.getColumns().stream()
                .anyMatch(Column::isPrimaryKey);
    }

    /**
     * Gets has auto generated primary key.
     *
     * @return whether the table has an auto generated primary key
     */
    public boolean getHasAutoGeneratedPrimaryKey() {
        return this.getColumns().stream()
                .anyMatch(column -> column.isPrimaryKey()
                        && column.getAutoIncrement() == Flag.YES);
    }

    /**
     * Gets escaped name.
     *
     * @return the escaped name
     */
    public String getEscapedName() {
        return this.database.escapedName(this.getTableName());
    }

    /**
     * Gets the highest pk index.
     *
     * @return the highest pk index
     */
    public int getHighestPKIndex() {
        return this.getColumns().stream()
                .filter(Column::isPrimaryKey)
                .mapToInt(Column::getPrimaryKeyIndex)
                .max()
                .orElse(0);
    }

    /**
     * Gets unique constraint group names.
     *
     * @return the unique constraint group names
     */
    public List<String> getUniqueConstraintGroupNames() {
        List<String> uniqueConstraintGroupNames = new ArrayList<String>();
        String prevUniqueConstraintGroupName = null;
        String uniqueConstraintGroupName = null;
        for (Column column : columns) {
            uniqueConstraintGroupName = column.getUniqueConstraintName();
            if (uniqueConstraintGroupName != null
                    && !uniqueConstraintGroupName.equals(
                    prevUniqueConstraintGroupName)) {
                uniqueConstraintGroupNames.add(uniqueConstraintGroupName);
                prevUniqueConstraintGroupName = uniqueConstraintGroupName;
            }
        }
        return uniqueConstraintGroupNames;
    }

    /**
     * Gets distinct custom column type names.
     *
     * @return the distinct custom column type names
     */
    public SortedSet<String> getDistinctCustomColumnTypeNames() {
        SortedSet<String> distinctColumnTypeNames = new TreeSet<>();

        distinctColumnTypeNames.addAll(columns.stream()
                .filter(column -> Table.class.getResource(
                        "/template/java/custom-object/"
                                + column.getTypeName().toLowerCase() + ".ftl")
                        != null)
                .map(Column::getTypeName).distinct()
                .collect(Collectors.toList()));

        return distinctColumnTypeNames;
    }

    /**
     * Gets distinct column type names.
     *
     * @return the distinct column type names
     */
    public SortedSet<String> getDistinctColumnTypeNames() {
        return columns.stream().map(Column::getTypeName)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public Database getDatabase() {
        return database;
    }

    /**
     * Gets table name.
     *
     * @return the table name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets table name.
     *
     * @param paramTableName the table name
     */
    public void setTableName(final String paramTableName) {
        this.tableName = paramTableName;
    }

    /**
     * Gets sequence name.
     *
     * @return the sequence name
     */
    public String getSequenceName() {
        return sequenceName;
    }

    /**
     * Sets sequence name.
     *
     * @param paramSequenceName the sequence name
     */
    public void setSequenceName(final String paramSequenceName) {
        this.sequenceName = paramSequenceName;
    }

    /**
     * Gets category name.
     *
     * @return the category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets category name.
     *
     * @param paramCategoryName the category name
     */
    public void setCategoryName(final String paramCategoryName) {
        this.categoryName = paramCategoryName;
    }

    /**
     * Gets schema name.
     *
     * @return the schema name
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * Sets schema name.
     *
     * @param paramSchemaName the schema name
     */
    public void setSchemaName(final String paramSchemaName) {
        this.schemaName = paramSchemaName;
    }

    /**
     * Gets table type.
     *
     * @return the table type
     */
    public TableType getTableType() {
        return tableType;
    }

    /**
     * Sets table type.
     *
     * @param paramTableType the table type
     */
    public void setTableType(final TableType paramTableType) {
        this.tableType = paramTableType;
    }

    /**
     * Gets remarks.
     *
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets remarks.
     *
     * @param paramRemarks the remarks
     */
    public void setRemarks(final String paramRemarks) {
        this.remarks = paramRemarks;
    }

    /**
     * Gets category type.
     *
     * @return the category type
     */
    public String getCategoryType() {
        return categoryType;
    }

    /**
     * Sets category type.
     *
     * @param paramCategoryType the category type
     */
    public void setCategoryType(final String paramCategoryType) {
        this.categoryType = paramCategoryType;
    }

    /**
     * Gets schema type.
     *
     * @return the schema type
     */
    public String getSchemaType() {
        return schemaType;
    }

    /**
     * Sets schema type.
     *
     * @param paramSchemaType the schema type
     */
    public void setSchemaType(final String paramSchemaType) {
        this.schemaType = paramSchemaType;
    }

    /**
     * Gets name type.
     *
     * @return the name type
     */
    public String getNameType() {
        return nameType;
    }

    /**
     * Sets name type.
     *
     * @param paramNameType the name type
     */
    public void setNameType(final String paramNameType) {
        this.nameType = paramNameType;
    }

    /**
     * Gets self referencing column name.
     *
     * @return the self referencing column name
     */
    public String getSelfReferencingColumnName() {
        return selfReferencingColumnName;
    }

    /**
     * Sets self referencing column name.
     *
     * @param paramSelfReferencingColumnName the self referencing column name
     */
    public void setSelfReferencingColumnName(
            final String paramSelfReferencingColumnName) {
        this.selfReferencingColumnName = paramSelfReferencingColumnName;
    }

    /**
     * Gets reference generation.
     *
     * @return the reference generation
     */
    public String getReferenceGeneration() {
        return referenceGeneration;
    }

    /**
     * Sets reference generation.
     *
     * @param paramReferenceGeneration the reference generation
     */
    public void setReferenceGeneration(final String paramReferenceGeneration) {
        this.referenceGeneration = paramReferenceGeneration;
    }

    /**
     * Gets columns.
     *
     * @return the columns
     */
    public List<Column> getColumns() {
        return columns;
    }

    /**
     * Sets columns.
     *
     * @param paramColumns the columns
     */
    public void setColumns(final List<Column> paramColumns) {
        this.columns = paramColumns;
    }

    /**
     * Gets indices.
     *
     * @return the indices
     */
    public List<Index> getIndices() {
        return indices;
    }

    /**
     * Sets indices.
     *
     * @param paramIndices the indices
     */
    public void setIndices(final List<Index> paramIndices) {
        this.indices = paramIndices;
    }

    /**
     * To print the table on the console.
     */
    @Override
    public String toString() {
        return tableName + "::" + tableType;
    }
}