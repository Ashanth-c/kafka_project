package com.cfa.objects.letter;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "letter")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Letter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "creationDate")
    @NonNull
    private Date creationDate;

    @Column(name = "treatmentDate")
    @NonNull
    private Date treatmentDate;

    @Column(name = "message")
    @NonNull
    private String message;
}
