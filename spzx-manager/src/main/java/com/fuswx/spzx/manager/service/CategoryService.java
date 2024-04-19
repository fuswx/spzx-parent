package com.fuswx.spzx.manager.service;

import com.fuswx.spzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<Category> findCategoryList(Integer id);

    //导出
    void exportData(HttpServletResponse response);

    //导入
    void importData(MultipartFile file);
}
