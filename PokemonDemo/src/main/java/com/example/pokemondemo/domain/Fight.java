package com.example.pokemondemo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.util.Set;

import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Fight {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String winnerName;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer oponentId;

    @Column(nullable = false)
    private Integer rounds;

    @Column(nullable = false)
    private LocalDate creation;

    @Column(nullable = false)
    private LocalDate updatee;

    @Column(nullable = false)
    private String league;

    @ManyToMany
    @JoinTable(
            name = "user_fight",
            joinColumns = @JoinColumn(name = "fight_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> userFightUsers;

}
