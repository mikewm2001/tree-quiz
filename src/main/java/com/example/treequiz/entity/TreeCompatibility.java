package com.example.treequiz.entity;

import com.example.treequiz.entity.enums.CompatibilityType;
import com.example.treequiz.entity.enums.TreeType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "tree_compatibility",
    uniqueConstraints = @UniqueConstraint(columnNames = {"source_tree", "target_tree", "type"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreeCompatibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_tree", nullable = false)
    private TreeType sourceTree;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_tree", nullable = false)
    private TreeType targetTree;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompatibilityType type;
}
