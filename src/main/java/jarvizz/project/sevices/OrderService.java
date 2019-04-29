package jarvizz.project.sevices;

import jarvizz.project.dao.OrderDao;
import jarvizz.project.models.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    public void save(Orders orders){
        orderDao.save(orders);
    }
}
