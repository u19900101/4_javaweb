package ppppp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ppppp.dao.BookDao;
import ppppp.g_dao.BookMapper;
import ppppp.pojo.Book;
import ppppp.pojo.BookExample;

import java.util.List;

/**
 * @author lppppp
 * @create 2021-01-04 9:32
 */
@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    BookMapper bookMapper;

    public int addBook(Book book) {
        int insert = bookMapper.insert(book);
        if(insert>0){
            System.out.println(" addBook succeed ...");
        }
        return insert;
    }

    public int updateBookById(Book book) {
        int i = bookMapper.updateByPrimaryKeySelective(book);
        if(i>0){
            System.out.println(" updateBookById succeed ...");
        }
        return i;
    }


    public int deleteBook(Integer id) {
        int i = bookMapper.deleteByPrimaryKey(id);
        if(i>0){
            System.out.println(" deleteBook succeed ...");
        }
        return i;

    }

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


    public int getSingleValue() {
        long integer = bookMapper.countByExample(new BookExample());
        return (int)integer;
    }

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
    public List<Book> selectByExample(BookExample bookExample) {
        return bookMapper.selectByExample(bookExample);
    }


}
