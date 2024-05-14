package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Balance balance;

    @ManyToOne
    private Accountant accountant;

    @OneToOne
    private Chat chat;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<FilesDB> filesDBList;

    public Client(Long id, String email, String phone, String firstName, String lastName, LocalDate dateOfBirth, Balance balance, Accountant accountant, Chat chat, List<FilesDB> filesDBList) {
        super(id, email, phone, firstName, lastName, dateOfBirth);
        this.balance = balance;
        this.accountant = accountant;
        this.chat = chat;
        this.filesDBList = filesDBList;
    }
}
