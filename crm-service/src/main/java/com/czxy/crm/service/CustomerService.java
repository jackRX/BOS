package com.czxy.crm.service;

import com.czxy.crm.dao.CustomerMapper;
import com.czxy.crm.domain.Customer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CustomerService
 * @Author
 * @Date 2018/9/7 17:05
 * Version 1.0
 **/
@Service
@Transactional
public class CustomerService {

    @Resource
    private CustomerMapper customerMapper;


    public List<Customer> findNoAssociationCustomers() {
        final Example example = new Example(Customer.class);
        example.createCriteria().andIsNull("fixedAreaId");
        final List<Customer> customers = customerMapper.selectByExample(example);
        return customers;
    }

    public List<Customer> findHasAssociationFixedAreaCustomers(String fixedAreaId) {
        final Example example = new Example(Customer.class);
        example.createCriteria().andEqualTo("fixedAreaId", fixedAreaId);
        final List<Customer> customers = customerMapper.selectByExample(example);
        return customers;
    }

    public void associationCustomersToFixedArea(String customerIdStr, String fixedAreaId) {
        List<Customer> list = findHasAssociationFixedAreaCustomers(fixedAreaId);
        for (Customer customer : list) {
            customer.setFixedAreaId(null);
            customerMapper.updateByPrimaryKey(customer);
        }
        // 切割字符串 1,2,3，组织新的关联关系
        if (StringUtils.isBlank(customerIdStr)) {
            return;
        }
        String[] customerIdArray = customerIdStr.split(",");
        for (String idStr : customerIdArray) {
            Integer id = Integer.parseInt(idStr);
            //查询
            Customer customer = customerMapper.selectByPrimaryKey(id);
            customer.setFixedAreaId(fixedAreaId);
            customerMapper.updateByPrimaryKey(customer);
        }

    }

    public void saveCustomer(Customer customer) {
        customerMapper.insert(customer);
    }

    public Customer findCustomerByTelephone(String telephone) {
        final Example example = new Example(Customer.class);
        final Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("telephone",telephone);
        final Customer customer = customerMapper.selectOneByExample(example);
        return customer;
    }

    public void updateCustomerByTelephone(String telephone) {
        Customer customer = findCustomerByTelephone(telephone);
        if(customer!=null){
            customer.setType(1);
            customerMapper.updateByPrimaryKey(customer);
        }
    }
}



