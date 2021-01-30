package ppppp.service;

import ppppp.pojo.Book;
import ppppp.pojo.BookExample;

import java.util.List;

/**
 * @author lppppp
 * @create 2021-01-04 11:18
 */
public interface BookService {

    int addBook(Book book);
    int updateBookById(Book book);
    int deleteBookById(Integer id);
    Book queryBookById(Integer id);
    List<Book> queryBooks();
    int getSingleValue();

    List<Book> selectByExample(BookExample bookExample);
}
