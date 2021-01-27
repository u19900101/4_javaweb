import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ppppp.dao.impl.BookDaoImpl;

/**
 * @author lppppp
 * @create 2021-01-27 23:58
 */
public class TestDao {
    BookDaoImpl bookDao = new BookDaoImpl();
    @Test
    public void T(){
        System.out.println(bookDao.getSingleValue());
    }
}
