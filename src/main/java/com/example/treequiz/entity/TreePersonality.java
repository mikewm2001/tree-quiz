package com.example.treequiz.entity;

import com.example.treequiz.entity.enums.TreeType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tree_personality")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreePersonality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TreeType code;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "tree_personality_traits",
        joinColumns = @JoinColumn(name = "tree_personality_id")
    )
    @Column(name = "trait")
    private List<String> traits;

    @Column(nullable = false)
    private String accentColor;
}
