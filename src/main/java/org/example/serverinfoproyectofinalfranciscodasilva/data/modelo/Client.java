package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderMethodName = "clientBuilder")
@AllArgsConstructor
@Table(name = "clients")
public class Client extends User{

    /*@ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Balance> balances;*/

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    private Accountant accountant;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    private Chat chat;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FilesDB> filesDBList;

    public Client(String email, String phone, String firstName, String lastName, LocalDate dateOfBirth, /*List<Balance> balances,*/ Accountant accountant, Chat chat, List<FilesDB> filesDBList) {
        super( email, phone, firstName, lastName, dateOfBirth);
        /*this.balances = balances;*/
        this.accountant = accountant;
        this.chat = chat;
        this.filesDBList = filesDBList;
    }
}
