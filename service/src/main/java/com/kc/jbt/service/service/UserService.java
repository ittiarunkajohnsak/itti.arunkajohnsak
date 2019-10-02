package com.kc.jbt.service.service;

import com.kc.jbt.service.entity.UserEntity;
import com.kc.jbt.service.entity.MemberType;
import com.kc.jbt.service.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(UserEntity entity) {
        entity.setRefCode(generateRefCode(entity.getPhone()));
        entity.setMemberType(MemberType.getMemberType(NumberUtils.toInt(entity.getSalary())));
        return userRepository.save(entity);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    private String generateRefCode(String phone) {
        if(StringUtils.isBlank(phone) || phone.length() < 4) {
            return StringUtils.EMPTY;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = sdf.format(new Date());

        int pLength = phone.length();
        String suffixPhone = phone.substring(pLength - 4, pLength);

        return String.format("%s%s", currentDate, suffixPhone);
    }
}
