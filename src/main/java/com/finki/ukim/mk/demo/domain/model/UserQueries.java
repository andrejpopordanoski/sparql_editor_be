package com.finki.ukim.mk.demo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_queries")
@EqualsAndHashCode
public class UserQueries {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
            @org.hibernate.annotations.Parameter(
                    name = "uuid_gen_strategy_class",
                    value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
            )
    }
    )
    private String id;

    @Column (name="user_email")
    private String userEmail;

    @Column (name="query_name", columnDefinition="VARCHAR(255) default 'Untitled' ")
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

    @Column (name = "private_access")
    private boolean privateAccess;

    @Column(name = "query_result", columnDefinition="MEDIUMTEXT")
    @JsonIgnore
    private String queryResult;

    @ManyToOne
    @JoinColumn(name="user_email", insertable = false, updatable = false)
    @JsonIgnore

    private User user;

}
