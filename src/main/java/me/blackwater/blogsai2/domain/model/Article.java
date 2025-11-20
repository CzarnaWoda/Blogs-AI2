package me.blackwater.blogsai2.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.blackwater.blogsai2.domain.exception.CommentNotFoundException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "blog_articles")
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String author;
    @Column(columnDefinition = "TEXT")
    private String content;

    private int views = 0;
    private int likes = 0;

    private boolean blocked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    private Instant createdAt;
    private Instant updatedAt;


    public Article(String title, String author, String content, Section section) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.section = section;

        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void addView(){
        this.updatedAt = Instant.now();
        this.views += 1;
    }
    public void addLike(){
        this.updatedAt = Instant.now();
        this.likes += 1;
    }

    public void block(){
        this.blocked = true;
    }

    public void unblock(){
        this.blocked = false;
    }
    public void addComment(Comment comment){
        this.updatedAt = Instant.now();

        comment.enable(this);

        this.comments.add(comment);
    }

    public void removeComment(Comment comment){
        this.updatedAt = Instant.now();

        this.comments.stream().filter(c -> c.getId() == comment.getId()).findFirst().orElseThrow(() -> new CommentNotFoundException("Comment has not been found"))
                .disable();
    }

    public void removeComment(long commentId){
        this.updatedAt = Instant.now();

        this.comments.stream().filter(c -> c.getId() == commentId).findFirst().orElseThrow(() -> new CommentNotFoundException("Comment has not been found"))
                .disable();
    }
}
