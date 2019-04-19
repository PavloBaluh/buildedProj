package jarvizz.project.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String address;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "userInfo")
    private CardInfo cardInfo;

    public UserInfo(String cardNumber, String cvv, String date) {
        this.cardInfo = new CardInfo(cardNumber,cvv,date);
    }

    public UserInfo(String name, String surname, String phoneNumber, String address, User user,String cardNumber, String cvv, String date) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.user = user;
        this.cardInfo = new CardInfo(cardNumber,cvv,date);
    }
}
