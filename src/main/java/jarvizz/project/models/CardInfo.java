package jarvizz.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class CardInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cardNumber;
    private String CVV;
    private String date;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private UserInfo userInfo;

    public CardInfo(String cardNumber, String CVV, String date) {
        this.cardNumber = cardNumber;
        this.CVV = CVV;
        this.date = date;
    }
}
