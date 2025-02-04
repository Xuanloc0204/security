package org.example.c08security.service.role;


import org.example.c08security.model.AppRole;
import org.example.c08security.service.GeneralService;

public interface IAppRoleService extends GeneralService<AppRole> {
    AppRole findByName(String name);
}
