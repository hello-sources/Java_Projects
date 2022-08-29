package com.ssm.service;

import com.github.pagehelper.PageInfo;
import com.ssm.pojo.ProductInfo;
import com.ssm.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {

    List<ProductInfo> getAll();

    PageInfo splitPage(int pageNum, int pageSize);

    int save(ProductInfo productInfo);

    ProductInfo getById(int pid);

    int update(ProductInfo productInfo);

    int delete(int pid);

    int deleteBatch(String []ids);

    List<ProductInfo> selectCondition(ProductInfoVo productInfoVo);

    PageInfo splitPageVo(ProductInfoVo vo, int pageSize);
}
