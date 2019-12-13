import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuan.dao.UserDao;
import com.xuan.dao.impl.UserDaoImpl;
import com.xuan.domain.User;
import com.xuan.service.UserService;
import com.xuan.service.impl.UserServiceImpl;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Test01 {
    @Test
    public void testUserDao_find()
    {
        UserDao userDao=new UserDaoImpl();
        User ansUser=userDao.findUser("12345678");
        System.out.println(ansUser);
    }
    @Test
    public void testUserDao_add()
    {
        User user=new User();
        user.setUsername("hahaha");
        user.setPassword("12345678");
        user.setName("张三");
        user.setBirthday("2019-10-09");
        user.setSex("m");
        user.setTelephone("13531180221");
        UserDao userDao=new UserDaoImpl();
        userDao.addUser(user);
    }
    @Test
    public void User_login()
    {
        User user=new User();
        user.setUsername("12345678");
        user.setPassword("12345678");
        UserDao userDao=new UserDaoImpl();
        System.out.println(userDao.loginUer("12345678","12345678"));
    }

    @Test
    public void loginService()
    {
        System.out.println("测试loginService");
        UserService userService=new UserServiceImpl();
        User user=new User();
        user.setUsername("12345678");
        user.setPassword("12345678");
        User ans=userService.login(user);
        System.out.println(ans);
    }
    @Test
    public void JsonTest() throws Exception
    {
        User user=new User();
        user.setUsername("我的名字");
        user.setTelephone("12312");
        ObjectMapper objectMapper=new ObjectMapper();
        String jsonUser=objectMapper.writeValueAsString(user);
        System.out.println(jsonUser);
    }
    @Test
    public void jsonTest02() throws Exception
    {
        User user=new User();
        user.setUsername("李1");

        User user1=new User();
        user1.setUsername("李2");

        User user2=new User();
        user2.setUsername("李3");

        List<User> users=new ArrayList<User>();
        users.add(user);
        users.add(user1);
        users.add(user2);

        ObjectMapper objectMapper=new ObjectMapper();
        String usersString=objectMapper.writeValueAsString(users);
        System.out.println(usersString);

    }
    @Test
    public void jsonTest03() throws Exception{
        Map<String,String> map=new LinkedHashMap<>();
        map.put("1","李1");
        map.put("2","李2");
        map.put("3","李3");
        ObjectMapper objectMapper=new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(map));
    }
    @Test
    public void jsonTest04() throws Exception{
        String myjson="{\"name\":\"张三\"}";
        ObjectMapper objectMapper=new ObjectMapper();
        User user=objectMapper.readValue(myjson,User.class);
        System.out.println(user);
    }
}
