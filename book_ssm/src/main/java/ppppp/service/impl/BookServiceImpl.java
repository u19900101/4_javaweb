package ppppp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ppppp.dao.BookDao;
import ppppp.pojo.Book;
import ppppp.pojo.BookExample;
import ppppp.service.BookService;

import java.util.List;

/**
 * @author lppppp
 * @create 2021-01-04 11:19
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    BookDao bookDao;
    @Override
    public int addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public int updateBookById(Book book) {
        return bookDao.updateBookById(book);
    }

    @Override
    public int deleteBookById(Integer id) {
        return bookDao.deleteBook(id);
    }


    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public int getSingleValue() {
        return bookDao.getSingleValue();
    }


    @Override
    public List<Book> selectByExample(BookExample bookExample) {
        return bookDao.selectByExample(new BookExample());
    }
}
