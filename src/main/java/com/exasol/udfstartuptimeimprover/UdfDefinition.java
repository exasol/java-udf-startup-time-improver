package com.exasol.udfstartuptimeimprover;

import java.util.List;

import lombok.Data;
import lombok.With;

/**
 * This class represents an Exasol script or adapter script definition.
 */
@Data
public class UdfDefinition {
    final String createStatementStart;
    final String createStatementEnd;
    @With
    private final String schema;
    private final String name;
    final String scriptClass;
    final List<String> jars;
    @With
    final List<String> jvmOptions;

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

    private String quote(final String identifier) {
        return "\"" + identifier.replace("\"", "\"\"") + "\"";
    }
}
