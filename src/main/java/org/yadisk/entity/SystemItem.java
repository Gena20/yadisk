package org.yadisk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@DynamicUpdate
@EqualsAndHashCode(of = "id")
public class SystemItem implements Serializable {
    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SystemItemType type;

    @Column(name = "size")
    private Long size;

    @Column(name = "url")
    private String url;

    @Column(name = "date", nullable = false)
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "FK_PARENT_ID"))
    @JsonBackReference
    private SystemItem parent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Set<SystemItem> children = new HashSet<>();

    public String getParentId() {
        if (parent != null) {
            return parent.getId();
        }

        return null;
    }

    public Set<SystemItem> getChildren() {
        if (type == SystemItemType.FILE) {
            return null;
        }

        return children;
    }
}
