package com.exasol.udfstartuptimeimprover;

import java.util.List;
import java.util.Objects;

/**
 * This class represents an Exasol script or adapter script definition.
 */
public final class UdfDefinition {
    private final String createStatementStart;
    private final String schema;
    private final String name;
    private final String createStatementEnd;
    private final String scriptClass;
    private final List<String> jars;
    private final List<String> jvmOptions;

    /**
     * Create a new instance.
     * 
     * @param createStatementStart start of the CREATE statement
     * @param schema               schema name
     * @param name                 UDF name
     * @param createStatementEnd   return type of the UDF
     * @param scriptClass          Java class of the UDF
     * @param jars                 BucketFs paths of the JAR files for the UDF
     * @param jvmOptions           additional JVM options for the UDF
     */
    public UdfDefinition(final String createStatementStart, final String schema, final String name,
            final String createStatementEnd, final String scriptClass, final List<String> jars,
            final List<String> jvmOptions) {
        this.createStatementStart = createStatementStart;
        this.schema = schema;
        this.name = name;
        this.createStatementEnd = createStatementEnd;
        this.scriptClass = scriptClass;
        this.jars = jars;
        this.jvmOptions = jvmOptions;
    }

    /**
     * Create a new instance with the given JVM options.
     * 
     * @param newJvmOptions new JVM options
     * @return updated {@link UdfDefinition}
     */
    public UdfDefinition withJvmOptions(final List<String> newJvmOptions) {
        return new UdfDefinition(this.createStatementStart, this.schema, this.name, this.createStatementEnd,
                this.scriptClass, this.jars, newJvmOptions);
    }

    /**
     * Create a new instance with the given schema name.
     * 
     * @param newSchema new schema name
     * @return updated {@link UdfDefinition}
     */
    public UdfDefinition withSchema(final String newSchema) {
        return new UdfDefinition(this.createStatementStart, newSchema, this.name, this.createStatementEnd,
                this.scriptClass, this.jars, this.jvmOptions);
    }

    private String quote(final String identifier) {
        return "\"" + identifier.replace("\"", "\"\"") + "\"";
    }

    /**
     * Get the start of the CREATE statement.
     * 
     * @return start of the CREATE statement
     */
    public String getCreateStatementStart() {
        return createStatementStart;
    }

    /**
     * Get the schema name.
     * 
     * @return schema name
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Get the UDF name.
     * 
     * @return UDF name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the return type of the UDF.
     * 
     * @return return type of the UDF
     */
    public String getCreateStatementEnd() {
        return createStatementEnd;
    }

    /**
     * Get the Java class of the UDF.
     * 
     * @return Java class of the UDF
     */
    public String getScriptClass() {
        return scriptClass;
    }

    /**
     * Get the BucketFs paths of the JAR files for the UDF.
     * 
     * @return BucketFs paths of the JAR files for the UDF
     */
    public List<String> getJars() {
        return jars;
    }

    /**
     * Get the additional JVM options for the UDF.
     * 
     * @return additional JVM options for the UDF
     */
    public List<String> getJvmOptions() {
        return jvmOptions;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(this.createStatementStart + " " + quote(this.schema) + "."
                + quote(this.name) + " " + this.createStatementEnd + "\n%scriptclass " + this.scriptClass + ";\n");
        if (!this.jvmOptions.isEmpty()) {
            builder.append("%jvmoption ").append(String.join(" ", this.jvmOptions)).append(";\n");
        }
        for (final String jar : this.jars) {
            builder.append("%jar ").append(jar).append(";\n");
        }
        builder.append("\n");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(createStatementStart, schema, name, createStatementEnd, scriptClass, jars, jvmOptions);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UdfDefinition other = (UdfDefinition) obj;
        return Objects.equals(createStatementStart, other.createStatementStart) && Objects.equals(schema, other.schema)
                && Objects.equals(name, other.name) && Objects.equals(createStatementEnd, other.createStatementEnd)
                && Objects.equals(scriptClass, other.scriptClass) && Objects.equals(jars, other.jars)
                && Objects.equals(jvmOptions, other.jvmOptions);
    }
}
