package ppppp.dao;

import ppppp.pojo.Book;
import ppppp.pojo.BookExample;

import java.util.List;

/**
 * @author lppppp
 * @create 2020-12-31 19:30
 */
public interface BookDao {
    int addBook(Book book);
    int updateBookById(Book book);
    int deleteBook(Integer id);
    Book queryBookById(Integer id);
    List<Book> queryBooks();
    int getSingleValue();

    int getCountByPrice(int min, int max);

    List<Book> selectByExample(BookExample bookExample);
}
