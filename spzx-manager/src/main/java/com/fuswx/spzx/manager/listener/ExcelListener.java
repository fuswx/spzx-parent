package com.fuswx.spzx.manager.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.fuswx.spzx.manager.mapper.CategoryMapper;
import com.fuswx.spzx.model.vo.product.CategoryExcelVo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

//监听器
@Slf4j
public class ExcelListener<T> implements ReadListener<T> {

    //每隔5条存储数据库，实际使用中可以100条，然后清理list，方便内存回收
    private static final int BATCH_COUNT = 100;

    //缓存的数据
    private List<T> categoryExcelVoList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    //构造传递mapper，操作数据库
    private CategoryMapper categoryMapper;

    public ExcelListener(CategoryMapper categoryMapper){
        this.categoryMapper = categoryMapper;
    }

    //从第二行开始读取，把每行读取内容封装到T对象里面
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        //把每行数据对象T放到list集合里面
        categoryExcelVoList.add(t);
        //达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容器OOM
        if (categoryExcelVoList.size() >= BATCH_COUNT){
            //调用方法一次性批量添加到数据库里面
            saveData();
            //清理list集合
            categoryExcelVoList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //保存数据
        saveData();
    }

    //保存的方法
    private void saveData() {
        categoryMapper.batchInsert((List<CategoryExcelVo>) categoryExcelVoList);
    }
}
