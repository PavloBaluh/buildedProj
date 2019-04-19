package jarvizz.project.sevices;

import jarvizz.project.dao.CardInfoDao;
import jarvizz.project.models.CardInfo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardInfoService {
    @Autowired
    CardInfoService cardInfoService;

    public void save(CardInfo cardInfo){
        cardInfoService.save(cardInfo);
    }
}
