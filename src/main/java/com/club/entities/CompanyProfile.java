package com.club.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class CompanyProfile {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String companyName;

    private String description;

    private Long employees;

    @OneToMany(mappedBy = "companyProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<WorkProfile> workProfiles;

    public CompanyProfile() {
    }
}
