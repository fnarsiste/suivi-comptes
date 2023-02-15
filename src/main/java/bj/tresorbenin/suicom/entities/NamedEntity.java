package bj.tresorbenin.suicom.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
public class NamedEntity extends BaseEntity {

    @Column(name = "libelle", nullable = false)
    protected String libelle;

    @Override
    public String toString() {
        return this.getLibelle();
    }

}
