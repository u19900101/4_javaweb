package ppppp.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ppppp.pojo.Book;
import ppppp.pojo.BookExample;
import ppppp.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lppppp
 * @create 2021-01-04 13:37
 */
@Controller
@RequestMapping("/manage/bookServlet")
public class BookServlet {
    @Autowired
    BookService bookService;

    @RequestMapping("/page")
    public String page(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       Model model) {
        PageHelper.startPage(pageNum, 5);
        //紧跟着的第一条查询语句才有用  后面的无分页功能
        List<Book> books = bookService.selectByExample(new BookExample());

        //传入要连续显示多少页
        PageInfo<Book> info = new PageInfo<>(books, 5);
        System.out.println("当前页码：" + info.getPageNum());//  5
        System.out.println("总记录数：" + info.getTotal());// 1010
        System.out.println("每页的记录数：" + info.getPageSize());// 1
        System.out.println("总页码：" + info.getPages());// 1010
        System.out.println("是否第一页：" + info.isIsFirstPage()); //false
        System.out.println("连续显示的页码："); //3 4 5 6 7

        model.addAttribute("info", info);

        // 带上当前的权限 路径  以便分页 区分跳转前缀
        model.addAttribute("url", "manage/bookServlet/page?");
        System.out.println(info);
        return "forward:/pages/manage/book_manage.jsp";

    }



    @RequestMapping(method = RequestMethod.POST)
    public String add(Book book, Model model) {
        System.out.println("come into add ...");

        if (book.getImgPath() == null) {
            book.setImgPath("static/img/default.jpg");
        }
        int i = bookService.addBook(book);
        System.out.println("added book Num:" + i);
        //插件具有自动纠错功能 免得写页面数，这样就直接查询最后一页了
        return "redirect:/manage/bookServlet/page?pageNum=10000";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String delete(Integer id, Integer pageNum) {
        System.out.println("come into delete ...");
        int i = bookService.deleteBookById(id);
        System.out.println("delete " + i);
        return "redirect:/manage/bookServlet/page?pageNum=" + pageNum;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(Book book,
                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        System.out.println("come into update ...");
        int i = bookService.updateBookById(book);
        System.out.println("update " + i);
        // request.getContextPath()可以返回当前页面所在的应用的名字;
        return "redirect:/manage/bookServlet/page?pageNum=" + pageNum;
    }

    // 为了传参使用
    @RequestMapping(method = RequestMethod.GET)
    public String getBook(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "id", defaultValue = "1") Integer id,
                          HttpServletRequest req) {
        System.out.println("come into getBook ...");

        Book book = bookService.queryBookById(id);
        System.out.println(book);
        req.setAttribute("book", book);
        return "forward:/pages/manage/book_edit.jsp?pageNum=" + pageNum;
    }
}