package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.pojo.ProductInfo;
import com.ssm.service.ProductInfoService;
import com.ssm.utils.FileNameUtil;
import com.ssm.vo.ProductInfoVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {

    public static final int PAGE_SIZE = 5;

    private String saveFileName = "";

    @Autowired
    private ProductInfoService productInfoService;

    @RequestMapping("/getall")
    public String getAll(HttpServletRequest request) {
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list", list);
        return "product";
    }


    @RequestMapping("/split")
    public String split(HttpServletRequest request) {
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("prodVo");
        if (vo != null) {
            info = productInfoService.splitPageVo((ProductInfoVo) vo, PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        } else {
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.setAttribute("info", info);
        return "product";
    }

    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session) {
        PageInfo info = productInfoService.splitPageVo(vo, PAGE_SIZE);
        session.setAttribute("info", info);
    }

    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo productInfoVo, HttpSession session) {
        List<ProductInfo> list = productInfoService.selectCondition(productInfoVo);
        session.setAttribute("list", list);
    }

    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request){
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        try {
            String path = request.getServletContext().getRealPath("/image_big");
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //为了在客户端显示图片，要将存储的文件名回传下去，由于是自定义的上传插件，所以此处要手工处理JSON
        JSONObject object = new JSONObject();
        object.put("imgurl", saveFileName);
        return object.toString();
    }

    @RequestMapping("/save")
    public String save(ProductInfo productInfo, HttpServletRequest request) {
        productInfo.setpImage(saveFileName);
        productInfo.setpDate(new Date());

        int num = -1;
        try {
            num = productInfoService.save(productInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            request.setAttribute("msg", "增加成功！");
        } else {
            request.setAttribute("msg", "增加失败！");
        }
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one")
    public String one(int pid, ProductInfoVo vo, Model model, HttpSession session) {
         ProductInfo info = productInfoService.getById(pid);
        model.addAttribute("prod", info);
        session.setAttribute("prodVo", vo);
        return "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info, HttpServletRequest request) {
        if (!saveFileName.equals("")) {
            info.setpImage(saveFileName);
        }
        int num = -1;
        try {
            num = productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            request.setAttribute("msg", "更新成功！");
        } else {
            request.setAttribute("msg", "更新失败！");
        }

        saveFileName = "";
        return "forward:/prod/split.action";
    }

    @RequestMapping("/delete")
    public String delete(int pid, ProductInfoVo vo, HttpServletRequest request) {
        int num = -1;
        try {
            num = productInfoService.delete(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            request.setAttribute("msg", "删除成功！");
            request.getSession().setAttribute("deleteprodVo", vo);
        } else {
            request.setAttribute("msg", "删除失败！");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit", produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request) {
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("deleteprodVo");
        if (vo != null) {
            info = productInfoService.splitPageVo((ProductInfoVo) vo, PAGE_SIZE);
        } else {
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.getSession().setAttribute("info", info);
        return request.getAttribute("msg");
    }


    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids, HttpServletRequest request) {
        String []ps = pids.split(",");
        try {
            int num = productInfoService.deleteBatch(ps);
            if (num > 0) {
                request.setAttribute("msg", "批量删除成功！");
            } else {
                request.setAttribute("msg", "批量删除失败！");
            }
        } catch (Exception e) {
            request.setAttribute("msg", "该商品不可删除！");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

}
