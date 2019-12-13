package com.xuan.web;

import com.xuan.service.ProductService;
import com.xuan.service.impl.ProductServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet("/upload/*")
public class UploadServlet extends BaseServlet{
    //存储文件
    private void saveFile(FileItem myfile){

    }
    public void uploadpic(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IOException {
        System.out.println("我被请求了");
        //设置ContentType字段值
        response.setContentType("text/html;charset=utf-8");
        // 创建DiskFileItemFactory工厂对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        File f = new File("\\fileTemp");
        if (!f.exists()) {
            f.mkdirs();
        }
        // 创建 ServletFileUpload对象
        ServletFileUpload fileupload = new ServletFileUpload(factory);
        //设置字符编码
        fileupload.setHeaderEncoding("utf-8");
        // 解析 request，得到上传文件的FileItem对象
        List<FileItem> fileitems = fileupload.parseRequest(request);
        //获取字符流
        PrintWriter writer = response.getWriter();
        FileItem myfile=null;
        String pname="";
        String pnote="";
        String spprice="";
        // 遍历集合
        for (FileItem fileitem : fileitems) {
            // 判断是否为普通字段
            if (fileitem.isFormField()) {
                // 获得字段名和字段值
                String name = fileitem.getFieldName();
                String value = fileitem.getString("utf-8");
                System.out.println(name+"值为"+value);
                if("pname".equals(name))
                    pname=value;
                if("pnote".equals(name))
                    pnote=value;
                if("pprice".equals(name))
                    spprice=value;
            }else{
                // 获取上传的文件
                if(fileitem.getSize()>0)
                    myfile=fileitem;
            }
        }

        double pprice=Double.valueOf(spprice.toString());
        ProductService productService=new ProductServiceImpl();
       int lastID= productService.addOne(pname,pnote,pprice);
       String filename="p"+lastID+".jpg";
        System.out.println("这里的last"+lastID+"");
        //存储文件
        if(myfile!=null){
        //处理上传文件
        if(filename != null && !filename.equals("")){
            writer.print("上传的文件名称是：" + filename + "<br>");
            // 在服务器创建同名文件
            String webPath = "/proImg/";
            //将服务器中文件夹路径与文件名组合成完整的服务器端路径
            String filepath = getServletContext().getRealPath(webPath + filename);
            System.out.println(filepath);
            // 创建文件
            File file = new File(filepath);
            file.getParentFile().mkdirs();
            file.createNewFile();
            // 获得上传文件流
            InputStream in = myfile.getInputStream();
            // 使用FileOutputStream打开服务器端的上传文件
            FileOutputStream out = new FileOutputStream(file);
            // 流的对拷
            byte[] buffer = new byte[1024];//每次读取1个字节
            int len;
            //开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
            while ((len = in.read(buffer)) > 0)
                out.write(buffer, 0, len);
            // 关闭流
            in.close();
            out.close();
            // 删除临时文件
            myfile.delete();
            writer.print("上传文件成功！<br>");
            writer.println("<a href=\"..\\uploadpic.html\">点击继续上传</a>");
        }
        }

    }

    public void changepro(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IOException{
        System.out.println("changepic被请求");

        String pnostr=request.getParameter("pno");
        int pno=-1;
        System.out.println("changepro:"+pnostr);
        if(pnostr!=null)
           pno=Integer.valueOf(pnostr);

        // 创建DiskFileItemFactory工厂对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        File f = new File("\\fileTemp");
        if (!f.exists()) {
            f.mkdirs();
        }
        // 创建 ServletFileUpload对象
        ServletFileUpload fileupload = new ServletFileUpload(factory);
        //设置字符编码
        fileupload.setHeaderEncoding("utf-8");
        // 解析 request，得到上传文件的FileItem对象
        List<FileItem> fileitems = fileupload.parseRequest(request);
        //获取字符流
        PrintWriter writer = response.getWriter();
        FileItem myfile=null;
        String pname="";
        String pnote="";
        String spprice="";
        // 遍历集合
        for (FileItem fileitem : fileitems) {
            // 判断是否为普通字段
            if (fileitem.isFormField()) {
                // 获得字段名和字段值
                String name = fileitem.getFieldName();
                String value = fileitem.getString("utf-8");
                System.out.println(name+"值为"+value);
                if("pname".equals(name))
                    pname=value;
                if("pnote".equals(name))
                    pnote=value;
                if("pprice".equals(name))
                    spprice=value;
            }else{
                // 获取上传的文件
                if(fileitem.getSize()>0){
                    System.out.println("有图片");
                    myfile=fileitem;
                }
            }
        }
        double pprice=-1;
        if(spprice!=null)
            pprice=Double.valueOf(spprice.toString());
        ProductService ps=new ProductServiceImpl();
        ps.updateOne(pname,pnote,pprice,pno);

        String filename="p"+pnostr+".jpg";
        System.out.println("这里的id"+pnostr+"");

        //存储文件
        if(myfile!=null){
            //处理上传文件
            if(filename != null && !filename.equals("")){
                writer.print("上传的文件名称是：" + filename + "<br>");
                // 在服务器创建同名文件
                String webPath = "/proImg/";
                //将服务器中文件夹路径与文件名组合成完整的服务器端路径
                String filepath = getServletContext().getRealPath(webPath + filename);
                System.out.println(filepath);
                // 创建文件
                File file = new File(filepath);
                file.getParentFile().mkdirs();
                file.createNewFile();
                // 获得上传文件流
                InputStream in = myfile.getInputStream();
                // 使用FileOutputStream打开服务器端的上传文件
                FileOutputStream out = new FileOutputStream(file);
                // 流的对拷
                byte[] buffer = new byte[1024];//每次读取1个字节
                int len;
                //开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
                while ((len = in.read(buffer)) > 0)
                    out.write(buffer, 0, len);
                // 关闭流
                in.close();
                out.close();
                // 删除临时文件
                myfile.delete();
                writer.print("修改商品成功！<br>");

            }
        }
        writer.println("<a href=\"..\\product_manage.html\">商品管理</a>");


    }
}
