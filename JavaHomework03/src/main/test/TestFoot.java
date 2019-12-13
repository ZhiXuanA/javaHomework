import com.xuan.dao.FootDao;
import com.xuan.dao.impl.FootDaoImpl;
import com.xuan.domain.User;
import com.xuan.util.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class TestFoot {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Test
    public void addOne(){
        String uname="12345678";
        String pno="12";
        String sqlCount="SELECT count(*) FROM tab_foot where uname=? and pno=?";
        int num=template.queryForObject(sqlCount, Integer.class ,uname, pno);
        System.out.println(num);
        if(num==0){
            String sqlInsert="INSERT INTO `tab_foot`(`uname`, `time`, `pno`) VALUES (?,?,?)";
            template.update(sqlInsert,uname,10,pno);
        }
        else{
            String sqlUpdate="UPDATE `tab_foot` SET time=time+10 where uname=? and pno=?";
            template.update(sqlUpdate,uname,pno);
        }
    }
    @Test
    public void testFindAll(){
        FootDao footDao=new FootDaoImpl();
        System.out.println(footDao.findAll());
    }
}
