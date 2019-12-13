import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuan.dao.CarDao;
import com.xuan.dao.impl.CarDaoImpl;
import com.xuan.domain.CarBean;
import com.xuan.domain.CarItem;
import com.xuan.service.CarService;
import com.xuan.service.impl.CarServiceImpl;
import com.xuan.util.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TestCar {
    @Test
    public void testAddCarDao(){
        CarDao carDao=new CarDaoImpl();
        carDao.addCar("1234",1);
    }
    @Test
    public void deleTest()
    {
        CarDao carDao=new CarDaoImpl();
        carDao.subOne("xuanxuan",1);
    }

    @Test
    public void findAll()
    {
        CarDao carDao=new CarDaoImpl();
        System.out.println(carDao.findAll("xuanxuan"));
    }
    @Test
    public void testTamplate()
    {
        String username="xuanxuan";
        JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
        String sql="SELECT pno,count(*) nums from tab_car where username=? group by pno limit 1";
        CarItem carItem=template.queryForObject(sql,new BeanPropertyRowMapper<CarItem>(CarItem.class),username);
        System.out.println(carItem);
    }
    @Test
    public void testTamplate2()
    {
        String username="xuanxuan";
        JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
        String sql="SELECT pno,count(*) nums from tab_car where username=? group by pno";
        List<CarItem> cars= template.query(sql,new BeanPropertyRowMapper<CarItem>(CarItem.class),username);
        System.out.println(cars);
    }
    @Test
    public void testService() throws JsonProcessingException {
        CarService carService=new CarServiceImpl();
        String username="xuanxuan";
        CarBean carBean=carService.findAll(username);
        System.out.println(carBean);
        ObjectMapper mapper = new ObjectMapper();
        String jso=mapper.writeValueAsString(carBean);
        System.out.println(jso);
    }
}
