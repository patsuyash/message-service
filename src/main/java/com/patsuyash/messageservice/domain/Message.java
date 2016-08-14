package com.patsuyash.messageservice.domain;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Message {

    @Id
    @NotNull
    @Size(max = 64)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @NotNull
    @Size(max = 64)
    @Column(name = "name", nullable = false)
    private String name;


    @Size(max = 128)
    @Column(name = "description", nullable = true)
    private String description;

    Message() {
    }

    public Message(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .toString();
    }
}
