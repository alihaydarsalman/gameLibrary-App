package com.turkcell.gamelibrary.system.entities.sourceEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private int id;

    @Column(name = "comment_body")
    private String commentBody;

    @CreationTimestamp
    @Column(name = "comment_date")
    private LocalDate commentDate;

    @UpdateTimestamp
    @Column(name = "comment_update_date")
    private LocalDate commentUpdateDate;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
