package com.example.demosecurity.service.role;


import com.example.demosecurity.model.AppRole;
import com.example.demosecurity.service.GeneralService;

public interface IAppRoleService extends GeneralService<AppRole> {
    AppRole findByName(String name);
}
