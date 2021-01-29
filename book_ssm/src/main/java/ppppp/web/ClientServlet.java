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

import java.math.BigDecimal;
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

        // 带上当前的 路径  以便分页 区分跳转前缀
        model.addAttribute("url", "client/bookServlet/page?");
        System.out.println(info);
        return "forward:/pages/client/index.jsp";
    }


    @RequestMapping("/queryByPrice")
    public String queryByPrice(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               Model model,
                               @RequestParam(value = "min", defaultValue = "0") BigDecimal min,
                               @RequestParam(value = "max", defaultValue = "999999")BigDecimal max) {
        PageHelper.startPage(pageNum, 4);
        //紧跟着的第一条查询语句才有用  后面的无分页功能
        BookExample bookExample = new BookExample();
        BookExample.Criteria criteria = bookExample.createCriteria();
        criteria.andPriceBetween(min, max);
        List<Book> books = bookMapper.selectByExample(bookExample);
        //传入要连续显示多少页
        PageInfo<Book> info = new PageInfo<>(books, 5);
        // 将查询条件带入
        model.addAttribute("url", "client/bookServlet/queryByPrice?min="+min+"&max="+max+"&");
        model.addAttribute("info", info);

        // 用于在页面显示数据
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        return "forward:/pages/client/index.jsp";
    }
}
