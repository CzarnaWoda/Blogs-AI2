package me.blackwater.blogsai2.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Entity(name = "section")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    private Instant createdAt;

    private Instant updatedAt;

    private String type;

    private boolean active;

    public Section(String title, String description, User creator, String type) {
        this.title = title;
        this.description = description;
        this.views = 0;
        this.creator = creator;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.type = type;
    }

    public void update(String title, String description, String type){
        this.title = title;
        this.description = description;
        this.type = type;

        this.updatedAt = Instant.now();
    }

    public void update(){
        this.updatedAt = Instant.now();
    }
    public void addView(){
        views++;
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
