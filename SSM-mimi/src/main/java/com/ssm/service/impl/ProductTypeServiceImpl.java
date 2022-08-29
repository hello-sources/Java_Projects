package com.ssm.service.impl;

import com.ssm.mapper.ProductTypeMapper;
import com.ssm.pojo.ProductType;
import com.ssm.pojo.ProductTypeExample;
import com.ssm.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
