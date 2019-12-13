import com.xuan.dao.OderDao;
import com.xuan.domain.OrderDetail;
import com.xuan.service.OderService;
import com.xuan.service.impl.OderServideImpl;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestOrder {
    @Test
    public void TestOrder(){
        Date da=new Date();
        System.out.println(da);
        SimpleDateFormat SFDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=SFDate.format(da);
        System.out.println(str);
    }
    @Test
    public void addTest(){
        OderService oderService=new OderServideImpl();
        oderService.addOne("12345","www12@qq.com","广东","{\"name\":\"123\"}",12,"2019-11-25 12:12:12");
    }
    @Test
    public void findAllTest() throws UnsupportedEncodingException {
        OderService oderService=new OderServideImpl();
        List<OrderDetail> ans=oderService.findAll();
        for (OrderDetail an : ans) {
           String car=an.getCar();
            System.out.println(car.toString());

        }
    }
}
