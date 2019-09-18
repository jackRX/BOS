package com.czxy.bos.service;

import com.czxy.bos.dao.MenuMapper;
import com.czxy.bos.system.Menu;
import com.czxy.bos.vo.DataGridResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName MenuService
 * @Author 宋明明
 * @Date 2018/10/12 08:25
 * Version 1.0
 **/
@Service
@Transactional
public class MenuService {
    @Resource
    private MenuMapper menuMapper;

    public DataGridResult queryMenuByPage(Integer page,Integer rows){
        PageHelper.startPage(page,rows);
        final List<Menu> menus = menuMapper.selectAll();
        final PageInfo<Menu> pageInfo = new PageInfo<>(menus);

        final DataGridResult result = new DataGridResult();
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }


    public void save(Menu menu) {
        //如果没有设置优先级priority，将使用当前分类中最大值+1
        if(menu.getPriority() == null){
            Example example = new Example(Menu.class);
            Example.Criteria criteria = example.createCriteria();
            if(menu.getPid() == null){
                //第一级别
                criteria.andIsNull("pid");
            }else{
                //其他级别
                criteria.andEqualTo("pid", menu.getPid());
            }
            int count = menuMapper.selectCountByExample(example);
            menu.setPriority(count + 1);
        }
        menuMapper.insert(menu);
    }
}
