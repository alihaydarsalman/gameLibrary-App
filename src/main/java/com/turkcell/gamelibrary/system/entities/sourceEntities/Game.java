package com.turkcell.gamelibrary.system.entities.sourceEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@Entity
@NoArgsConstructor
@Data
@Table(name = "games")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "Lazy"})
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Integer id;

    @Column(name = "game_name", unique = true)
    private String gameName;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private Double price;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ManyToOne
    @JoinTable(name = "game_campaign",
            joinColumns = {@JoinColumn(name = "game_id")},
            inverseJoinColumns = {@JoinColumn(name = "campaign_id")})
    private Campaign campaign;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Comment> commentList;
}
