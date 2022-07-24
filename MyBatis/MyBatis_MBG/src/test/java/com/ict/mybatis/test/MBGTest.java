package com.ict.mybatis.test;

import com.ict.mybatis.bean.Emp;
import com.ict.mybatis.bean.EmpExample;
import com.ict.mybatis.mapper.EmpMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MBGTest {
    @Test
    public void testMBG() {
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);

            //查询所有数据
//            List<Emp> list = mapper.selectByExample(null);
//            list.forEach(emp -> System.out.println(emp));

            //根据条件查询
//            EmpExample empExample = new EmpExample();
//            empExample.createCriteria().andEmpNameEqualTo("张三").andAgeGreaterThan(20);
//            empExample.or().andDidIsNotNull();
//            List<Emp> list = mapper.selectByExample(empExample);
//            list.forEach(emp -> System.out.println(emp));

            mapper.updateByPrimaryKeySelective(new Emp(1, "admin", 22, null, "432@qq.com", 3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
