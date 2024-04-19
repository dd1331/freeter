package com.hobos.freeter.post;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@ToString(exclude = {"parent", "children"})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Category> children = new ArrayList<>();

    public void addChildren(Category child) {
        this.children.add(child);
        child.registerParent(this);
    }

    void registerParent(Category category) {
        this.parent = category;

    }
}