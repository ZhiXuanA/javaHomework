import com.xuan.dao.ProductDao;
import com.xuan.dao.impl.ProductDaoImpl;
import com.xuan.domain.PageBean;
import com.xuan.domain.Product;
import com.xuan.service.ProductService;
import com.xuan.service.impl.ProductServiceImpl;
import org.junit.Test;

import java.util.List;

public class TestProduct {
    @Test
    public void pageProduct(){
        ProductDao pd=new ProductDaoImpl();
        List<Product> plist=pd.findAll(0,100,"1");
        System.out.println(plist);
    }
    @Test
    public void findTotalProduct(){
        ProductDao pd=new ProductDaoImpl();
        System.out.println(pd.findTotal(""));
    }
    @Test
    public void testPage(){
        ProductService productService=new ProductServiceImpl();
        productService.pageQuery(1,100,"");
    }
    @Test
    public void testPageBean(){
        ProductService productService=new ProductServiceImpl();
        PageBean<Product> pageBean=productService.pageQuery(1,100,"");
        System.out.println(pageBean);
    }
    @Test
    public void testAddproduct(){
        ProductDao pdao=new ProductDaoImpl();
        pdao.addOne("小米米","这里是备注",1212);
    }
    @Test
    public void findAddproduct(){
        ProductDao pdao=new ProductDaoImpl();
        System.out.println(pdao.findOne(1));
    }
    @Test
    public void allProduct(){
       ProductService productService=new ProductServiceImpl();
       List<Product> products=productService.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }
    @Test
    public void deleteProduct(){
       ProductDao pd=new ProductDaoImpl();
       pd.delectOne(20);
    }
    @Test
    public void deleteProductSer(){
        ProductService productService=new ProductServiceImpl();
        productService.deleteOnePro(21);
    }
    @Test
    public void updateProductDao(){
       ProductDao pd=new ProductDaoImpl();
       pd.updateOne("xinx","哈哈",912,27);
    }
    @Test
    public void updateProductSer(){
        ProductService ps=new ProductServiceImpl();
        ps.updateOne("瓦克达",null,9,27);
    }
}
