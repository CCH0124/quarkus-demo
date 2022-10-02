package org.cch.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.cch.definition.ERole;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class RoleEntity extends PanacheEntityBase {

    @Id
    @Column(nullable = false, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    public UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 20)
//    @Basic
//    @Convert(converter = ERoleAttributeConverter.class)
    public ERole name;


    public static  RoleEntity findByName(ERole name){
        return find("name", name).firstResult();

    }
}
