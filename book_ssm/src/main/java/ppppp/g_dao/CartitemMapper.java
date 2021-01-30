package ppppp.g_dao;

import org.apache.ibatis.annotations.Param;
import ppppp.pojo.Cartitem;
import ppppp.pojo.CartitemExample;

import java.util.List;

public interface CartitemMapper {
    long countByExample(CartitemExample example);

    int deleteByExample(CartitemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Cartitem record);

    int insertSelective(Cartitem record);

    List<Cartitem> selectByExample(CartitemExample example);

    Cartitem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Cartitem record, @Param("example") CartitemExample example);

    int updateByExample(@Param("record") Cartitem record, @Param("example") CartitemExample example);

    int updateByPrimaryKeySelective(Cartitem record);

    int updateByPrimaryKey(Cartitem record);
}