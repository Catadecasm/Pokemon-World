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
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Fight {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer id;

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
