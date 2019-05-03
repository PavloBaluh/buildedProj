package jarvizz.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Orders implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String date;
    @Nationalized
    private String name;
    @Nationalized
    private String surname;
    @Nationalized
    private String address;
    private String phoneNumber;
    private double bonus = 0.0;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REMOVE,CascadeType.REFRESH,},mappedBy = "orders")
    private List<Food>foods = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY,cascade =  {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REMOVE,CascadeType.REFRESH,})
    private User user;
}
