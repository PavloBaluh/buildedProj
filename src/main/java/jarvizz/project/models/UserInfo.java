package jarvizz.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Data
@ToString(exclude = {"user","cardInfo"})
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Nationalized
    private String name;
    @Nationalized
    private String surname;
    private String phoneNumber;
    @Nationalized
    private String address;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "userInfo")
    private CardInfo cardInfo;

    public UserInfo(String cardNumber, String cvv, String date) {
        this.cardInfo = new CardInfo(cardNumber,cvv,date);
    }

    public UserInfo(String name, String surname, String phoneNumber, String address) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
