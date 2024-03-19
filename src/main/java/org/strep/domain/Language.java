/*-
 * #%L
 * STRep
 * %%
 * Copyright (C) 2019 - 2024 SING Group (University of Vigo)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.strep.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * JPA Bean for the Dataset objects managed by application
 * 
 * @author Ismael Vázquez
 * @author José Ramón Méndez
 */
@Entity
public class Language implements Serializable {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The language id of the language
     */
    @Id
    @NotNull
    @Size(min = 1, max = 4, message = "Name must have beetween 1 and 4 characters")
    @Column(length = 4, columnDefinition = "VARCHAR(4)")
    private String language;

    /**
     * The description of the language
     */
    @NotNull
    @Size(min = 1, max = 25, message = "The description of the dataset must have between 1 and 25 characters long")
    @Column(length = 25, columnDefinition = "VARCHAR(25)")
    private String description;

    /**
     * The datasets associated to this language
     */
    @ManyToMany(mappedBy = "language")
    private Set<Dataset> datasets;

    /**
     * The default constructor
     */
    public Language() {

    }

    /**
     * Constructor with the language key and the description.
     * @param language
     * @param description
     */
    public Language(String language, String description) {
        this.language = language;
        this.description = description;
    }    

    /**
     * Returns the description of the language
     * @return the description of the language 
     */
    public String getDescription() {
        return description;
    }

    /**
     * Stablish the description of the language
     * @param description The description of the language
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return datasets associated to this language
     * 
     * @return datasets associated to this language
     */
    public Set<Dataset> getDatasets()
    {
        return this.datasets;
    }

    /**
     * Stablish the datasets associated to this language
     * @param datasets associated to this language
     */
    public void setDatasets(Set<Dataset> datasets)
    {
        this.datasets = datasets;
    }

    /**
     * Returns the name of the language
     * @return the name of the language
     */ 
    public String getLanguage()
    {
        return this.language;
    }

    /**
     * Stablish the name of the language
     * @param language the name of the language
     */
    public void setLanguage(String language)
    {
        this.language = language;
    }


}
