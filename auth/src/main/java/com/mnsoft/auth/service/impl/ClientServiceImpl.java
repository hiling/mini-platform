package com.mnsoft.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mnsoft.auth.mapper.ClientMapper;
import com.mnsoft.auth.model.Client;
import com.mnsoft.auth.service.ClientService;
import org.springframework.stereotype.Service;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/9/2018.
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper,Client> implements ClientService {

}
