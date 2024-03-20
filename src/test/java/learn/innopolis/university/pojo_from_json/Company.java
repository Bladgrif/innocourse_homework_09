package learn.innopolis.university.pojo_from_json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Company {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    public Company setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public Company setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return description;
    }
}