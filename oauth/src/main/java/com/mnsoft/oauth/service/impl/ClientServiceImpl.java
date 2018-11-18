package com.mnsoft.oauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mnsoft.oauth.mapper.ClientMapper;
import com.mnsoft.oauth.model.Client;
import com.mnsoft.oauth.service.ClientService;
import org.springframework.stereotype.Service;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/9/2018.
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper,Client> implements ClientService {

}
