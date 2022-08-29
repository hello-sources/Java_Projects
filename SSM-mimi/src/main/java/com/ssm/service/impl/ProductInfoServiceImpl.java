package com.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.mapper.ProductInfoMapper;
import com.ssm.pojo.ProductInfo;
import com.ssm.pojo.ProductInfoExample;
import com.ssm.service.ProductInfoService;
import com.ssm.vo.ProductInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //进行有条件的查询工作，必须创建productInfoExample对象
        ProductInfoExample example = new ProductInfoExample();
        example.setOrderByClause("p_id desc");
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(new ProductInfoExample());
        //将查到的集合进行封装进PageInfo
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(productInfos);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo productInfo) {
        return productInfoMapper.insert(productInfo);
    }

    @Override
    public ProductInfo getById(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo productInfo) {
        return productInfoMapper.updateByPrimaryKey(productInfo);
    }

    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo productInfoVo) {
        return productInfoMapper.selectCondition(productInfoVo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        PageHelper.startPage(vo.getPage(), pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        return new PageInfo<>(list);
    }
}
