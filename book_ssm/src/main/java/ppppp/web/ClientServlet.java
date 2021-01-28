package ppppp.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ppppp.g_dao.BookMapper;
import ppppp.pojo.Book;
import ppppp.pojo.BookExample;
import ppppp.service.impl.BookServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/client/bookServlet")
public class ClientServlet{
    @Autowired
    BookServiceImpl bookService;
    @Autowired
    BookMapper bookMapper;
    @RequestMapping("/page")
    public String page(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       Model model){
        System.out.println("come into ClientServlet...");
        PageHelper.startPage(pageNum, 4);
        //紧跟着的第一条查询语句才有用  后面的无分页功能
        List<Book> books = bookMapper.selectByExample(new BookExample());
        //传入要连续显示多少页
        PageInfo<Book> info = new PageInfo<>(books, 5);
        model.addAttribute("info", info);

        // 带上当前的权限 路径  以便分页 区分跳转前缀
        model.addAttribute("controllerName", "client");
        System.out.println(info);
        return "forward:/pages/client/index.jsp";
    }


   /* public void queryByPrice(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("come into queryByPrice...");
        int min = WebUtils.parseInt(req.getParameter("min"), 0);
        int max = WebUtils.parseInt(req.getParameter("max"), 100000);
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);

        Page<Book> page = bookService.getPageListByPrice(pageNo,max,min);
        page.setUrl("client/bookServlet?action=queryByPrice&min="+min+"&max="+max);
        req.setAttribute("page", page);
        // 用于回显 查询的 价格数据
        *//*if(!"".equalsIgnoreCase(req.getParameter("max"))){
            req.setAttribute("max", max);
        }
        if(!"".equalsIgnoreCase(req.getParameter("min"))){
            req.setAttribute("min", min);
        }*//*
        req.setAttribute("min", req.getParameter("min"));
        req.setAttribute("max", req.getParameter("max"));
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,res);
    }
*/
}
