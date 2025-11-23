package me.blackwater.blogsai2.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.blackwater.blogsai2.domain.exception.ObjectRequirementsException;

import java.time.Instant;

@Entity(name = "comment")
@Table(name = "article_comments")
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String author;
    @Column(columnDefinition = "TEXT")
    private String value;

    private Instant createdAt;


    private int views;
    private int likes;

    private boolean disabled = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;


    public Comment(String author, String value, int views, int likes) {
        this.author = author;
        this.value = value;
        this.createdAt = Instant.now();
        this.views = views;
        this.likes = likes;
    }

    public void disable() {
        this.disabled = true;
    }
    public void enable(Article article){
        this.article = article;
        this.disabled = false;
    }
    public void enable(){
        if(this.article != null){
            this.disabled = false;
        }else{
            throw new ObjectRequirementsException("Article for this comment has not been found, comment can't be enabled without Article");
        }
    }

}
