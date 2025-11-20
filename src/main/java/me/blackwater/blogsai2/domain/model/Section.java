package me.blackwater.blogsai2.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Entity
@Table(name = "blog_sections")
@NoArgsConstructor
@Getter
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private String description;

    private int views;

    private int clicks;

    private String creator;

    private Instant createdAt;

    private Instant updatedAt;

    private String type;

    private boolean active;

    public Section(String title, String description, int views, int clicks, String creator, Instant createdAt, Instant updatedAt, String type) {
        this.title = title;
        this.description = description;
        this.views = views;
        this.clicks = clicks;
        this.creator = creator;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.type = type;

        this.createdAt = Instant.now();

        this.updatedAt = Instant.now();
    }

    public void active(){
        this.updatedAt = Instant.now();
        this.active = true;
    }
    public void inactive(){
        this.updatedAt = Instant.now();
        this.active = false;
    }


}
