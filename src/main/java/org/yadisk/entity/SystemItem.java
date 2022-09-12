package org.yadisk.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class SystemItem {
    @NotBlank
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "parent_id")
    private String parentId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SystemItemType type;

    @Column(name = "size")
    private Integer size;

    @Column(name = "url")
    private String url;
}
