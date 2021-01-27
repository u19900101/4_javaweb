package ppppp.generate_dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
// import ppppp.generate_bean.Orderitem;
// import ppppp.generate_bean.Orderitem;
import ppppp.generate_bean.Orderitem;
import ppppp.pojo.OrderItem;
// import ppppp.generate_bean.Orderitem;
import ppppp.generate_bean.OrderitemExample;

public interface OrderitemMapper {
    long countByExample(OrderitemExample example);

    int deleteByExample(OrderitemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Orderitem record);

    int insertSelective(Orderitem record);

    List<Orderitem> selectByExample(OrderitemExample example);

    Orderitem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Orderitem record, @Param("example") OrderitemExample example);

    int updateByExample(@Param("record") Orderitem record, @Param("example") OrderitemExample example);

    int updateByPrimaryKeySelective(Orderitem record);

    int updateByPrimaryKey(Orderitem record);
}