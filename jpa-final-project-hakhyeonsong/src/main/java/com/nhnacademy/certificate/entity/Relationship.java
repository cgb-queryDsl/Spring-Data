package com.nhnacademy.certificate.entity;

public enum Relationship {
    부("father"),
    모("mother"),
    배우자("spouse"),
    자녀("child"),
    동거친족("cohabiting relatives"),
    비동거친족("non-living relatives"),
    동거자("housemate"),
    본인("");

    private final String relationship;

    Relationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRelationship() {
        return relationship;
    }
}
