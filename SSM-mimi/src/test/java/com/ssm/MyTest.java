package com.ssm;

import com.ssm.mapper.ProductInfoMapper;
import com.ssm.pojo.ProductInfo;
import com.ssm.utils.MD5Util;
import com.ssm.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml", "classpath:applicationContext_service.xml"})
public class MyTest {

    @Autowired
    private ProductInfoMapper mapper;

    @Test
    public void testMD5() {
        String mi = MD5Util.getMD5("000000");
        System.out.println(mi);
    }

    @Test
    public void testSelectCondition() {
        ProductInfoVo productInfoVo = new ProductInfoVo();
        productInfoVo.setPname("4");
        productInfoVo.setTypeid(3);
        productInfoVo.setLprice(3000.0);
        productInfoVo.setHprice(4000.0);
        List<ProductInfo> list = mapper.selectCondition(productInfoVo);
        list.forEach(productInfo -> System.out.println(productInfo));
    }

}