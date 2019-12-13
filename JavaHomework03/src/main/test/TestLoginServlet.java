import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/test")
public class TestLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String user=null;
        try {
          user=(String)req.getSession().getAttribute("user");
      }catch (Exception e)
        {

        }
        if(user!=null) {
            System.out.println(user + "是用户！！！");
            resp.setCharacterEncoding("utf8");
            resp.getWriter().write(user+"是当前用户名");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
