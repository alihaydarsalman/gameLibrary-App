package com.turkcell.gamelibrary.system.entities.sourceEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "campaigns")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "Lazy"})
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id")
    private Integer id;

    @Column(name = "campaign_title", unique = true)
    private String campaignTitle;

    @Column(name = "campaign_desc")
    private String campaignDescription;

    @Column(name = "campaign_start_date")
    private LocalDate startDate;

    @Column(name = "campaign_end_date")
    private LocalDate endDate;

    @Column(name = "discount_amount")
    private Double discountAmount;

    @OneToMany(mappedBy = "campaign")
    private List<Game> gameList;
}

