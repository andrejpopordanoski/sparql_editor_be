package com.finki.ukim.mk.demo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_queries")
@EqualsAndHashCode
public class UserQueries {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (name="user_email")
    private String userEmail;

    @Column (name="queryName", columnDefinition="VARCHAR(255) default 'Untitled' ")
    private String queryName;

    @Column (name="query_name_suffix", columnDefinition="VARCHAR(255) default '' ")
    private String queryNameSuffix;

    @Column (name="url")
    private String url;

    @Column(name="default_dataset_name")
    private String defaultDatasetName;

    @Column (name = "query_string")
    private String queryString;

    @Column (name = "format")
    private String format;

    @Column (name = "timeout")
    private int timeout;

    @Column(name = "query_result", columnDefinition="MEDIUMTEXT default 'Result was too big for storing in the database'")
    @JsonIgnore
    private String queryResult;

    @ManyToOne
    @JoinColumn(name="user_email", insertable = false, updatable = false)
    @JsonIgnore

    private User user;

}
