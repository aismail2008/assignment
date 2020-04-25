package com.company.applications.api;

import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

public class CompanyArtifact {
    @Valid
    @Length(min = 1)
    public String packageName;

    @Valid
    public String[] artifact;

    @Valid
    public String[] version;

    @Length(min = 1)
    public String deprecationDate;

    /**
     * The default constructor is required by Jackson to deserialize JSON representations of objects.
     */
    public CompanyArtifact() {
    }

    public CompanyArtifact(String packageName, String[] artifact, String[] version, String deprecationDate) {
        this.packageName = packageName;
        this.artifact = artifact;
        this.version = version;
        this.deprecationDate = deprecationDate;
    }

    public CompanyArtifact(String packageName, String artifact, String version, String deprecationDate) {
        this(packageName, new String[]{artifact}, new String[]{version}, deprecationDate);
    }

    public String artifactFullPath() {
        return packageName.replace(".", "/").concat("/" + artifact[0]).concat("/" + version[0]);
    }

    public String artifactFilesTemplate() {
        return packageName.replace(".", "/").concat("/" + artifact[0]).concat("/" + version[0]).concat("/" + artifact[0] + "-" + version[0]);
    }
}