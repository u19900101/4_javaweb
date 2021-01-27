package ppppp.dao.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ppppp.dao.BookDao;
import ppppp.generate_bean.BookExample;
import ppppp.generate_dao.BookMapper;
import ppppp.pojo.Book;

import java.util.List;

/**
 * @author lppppp
 * @create 2021-01-04 9:32
 */
@Repository
@Transactional
public class BookDaoImpl implements BookDao {

    @Autowired
    BookMapper bookMapper;
    @Override
    public int addBook(Book book) {
        int insert = bookMapper.insert(book);
        if(insert>0){
            System.out.println(" addBook succeed ...");
        }
        return insert;
    }

    @Override
    public int updateBookById(Book book) {
        int i = bookMapper.updateByPrimaryKeySelective(book);
        if(i>0){
            System.out.println(" updateBookById succeed ...");
        }
        return i;
    }

    @Override
    public int deleteBook(Integer id) {
        int i = bookMapper.deleteByPrimaryKey(id);
        if(i>0){
            System.out.println(" deleteBook succeed ...");
        }
        return i;

    }

    @Override
    public Book queryBookById(Integer id) {
        Book book = null;
        try {
            book = bookMapper.selectByPrimaryKey(id);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            return book;
        }
    }

    @Override
    public List<Book> queryBooks() {

        List<Book> bookList = null;
        try {
            bookList = bookMapper.selectByExample(new BookExample());
        }catch (Exception e){
            System.out.println(e);
        }finally {
            return bookList;
        }
    }

    @Override
    public int getSingleValue() {
        long integer = bookMapper.countByExample(new BookExample());
        return (int)integer;
    }


    @Override
    public List<Book> getPageList(int begin, int size) {
        List<Book> bookList =null;
        try {
            PageHelper.startPage(begin, size);

            //紧跟着的第一条查询语句才有用  后面的无分页功能
            List<Book> books = bookMapper.selectByExample(new BookExample());
            //传入要连续显示多少页
            PageInfo<Book> bookPageInfo = new PageInfo<>(books, 5);
            for (Book book : books) {
                System.out.println(book);
            }
            bookList = bookPageInfo.getList();
        }catch (Exception e){
            System.out.println(e);
        }finally {
            return bookList;
        }
    }

    @Override
    public int getCountByPrice(int min, int max) {
        BookExample bookExample = new BookExample();
        // 按照字段升序或者降序
        bookExample.setOrderByClause("price DESC");
        // 创建一个查询准则
        BookExample.Criteria criteria = bookExample.createCriteria();
        criteria.andIdBetween(min, max);
        long l = bookMapper.countByExample(bookExample);
        return (int) l;
    }

    @Override
    public List<Book> getPageListByPrice(int min, int max, int begin, int size) {
        List<Book> bookList =null;
        try {
            PageHelper.startPage(begin, size);
            BookExample bookExample = new BookExample();
            // 按照字段升序或者降序
            bookExample.setOrderByClause("price DESC");
            // 创建一个查询准则
            BookExample.Criteria criteria = bookExample.createCriteria();
            criteria.andIdBetween(min, max);
            //紧跟着的第一条查询语句才有用  后面的无分页功能
            List<Book> books = bookMapper.selectByExample(bookExample);
            //传入要连续显示多少页
            PageInfo<Book> bookPageInfo = new PageInfo<>(books, 5);
            for (Book book : books) {
                System.out.println(book);
            }
            bookList = bookPageInfo.getList();
        }catch (Exception e){
            System.out.println(e);
        }finally {
            return bookList;
        }
    }
}
