/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bj.tresorbenin.suicom.entities.base;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseEntity implements Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("ID de la ligne (sequentiel).")
    protected Long id;

    @Column(name = "code", length = 32)
    @Comment("Code auto généré via trigger ou fourni.")
    protected String code;

    @Basic
    @Comment("Date de creation.")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateCreation;

    @Basic
    @Column(name = "date_cessation")
    @Comment("Date de cessation.")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateCessation;

    @Comment("Compte user ayant modifié.")
    protected String modifierPar;

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public BaseEntity clone() {
        try {
            return (BaseEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
