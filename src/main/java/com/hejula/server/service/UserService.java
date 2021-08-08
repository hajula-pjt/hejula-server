package com.hejula.server.service;

import com.hejula.server.entities.Customer;
import com.hejula.server.entities.CustomerTag;
import com.hejula.server.entities.FileEntity;
import com.hejula.server.repository.CustomerRepository;
import com.hejula.server.repository.CustomerTagRepository;
import com.hejula.server.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final CustomerRepository customerRepository;
    private final CustomerTagRepository customerTagRepository;
    private final FileUtil fileUtil;

    public boolean isExistCustomerId(String id) {
        return customerRepository.existsById(id);
    }

    public Customer updateCustomer(Customer customer, List<String> tagList) throws Exception {
        return setCustomer(customer, null, tagList);
    }

    @Transactional
    public Customer setCustomer(Customer customer, MultipartFile img, List<String> tagList) throws Exception {
        customer = customerRepository.save(customer);

        if(img != null) {
            List<MultipartFile> list = new ArrayList<>();
            list.add(img);

            List<FileEntity> fileList = fileUtil.makeFile(list);
            for (FileEntity fileEntity : fileList) {
                fileEntity.setCustomer(customer);
            }
        }

        if(tagList != null) {
            List<CustomerTag> customerTags = new ArrayList<>();
            for (String s : tagList) {
                CustomerTag tag = new CustomerTag();
                tag.setName(s);
                customerTags.add(tag);
            }
            customerTagRepository.saveAll(customerTags);
        }

        return customer;
    }

    public Customer getCustomer(Customer customer){
        return customerRepository.findByIdAndPassword(customer.getId(), customer.getPassword());
    }


}
