package com.balasamajam.services;

import com.balasamajam.entities.Admin;
import com.balasamajam.models.AdminBaseModel;
import com.balasamajam.models.ResponseBaseModel;
import com.balasamajam.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public void save(Admin admin) {
        adminRepository.save(admin);
    }

    public ResponseBaseModel<List<AdminBaseModel>> getAllAdmins()
    {
        ResponseBaseModel<List<AdminBaseModel>> response = new ResponseBaseModel<>();
        response.setStatus("OK");
        response.setMessage("Successfully fetched all admins");

        try
        {
            List<Admin> adminList =  adminRepository.findAll();

            List<AdminBaseModel> adminModelList = new ArrayList<>();
            for (Admin admin : adminList)
            {
                AdminBaseModel adminBaseModel = new AdminBaseModel();
                adminBaseModel.setAdminid(admin.getUuid().toString());
                adminBaseModel.setAdminFullName(admin.getFirstName() + " " + admin.getLastName());

                adminModelList.add(adminBaseModel);
            }

            response.setData(adminModelList);
        }
        catch (Exception e)
        {
            response.setStatus("ERROR");
            response.setMessage("Error while fetching admin details");
            e.printStackTrace();
        }

        return response;
    }
}
