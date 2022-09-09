package com.kepler.constant;

public class SystemConstant {

    public interface Url {
         String ADMIN_HOME = "/admin/home";
    }

    public static final String MODEL = "model";

    public interface User {
        String PASSWORD_DEFAULT = "123456";
        Integer STATUS_ACTIVE = 1;
        Integer STATUS_NOT_ACTIVE = 0;
        String ROLE_STAFF = "ROLE_staff";
        String ROLE_MANAGER = "ROLE_manager";
        String MANAGER = "manager";
        String STAFF = "staff";
    }

    public interface File {
        String FILE_UPLOAD_ROOT_FOLDER = "value.file.upload.root.folder";
        String STATIC_RESOURCE_URL = "/files/";
    }

    public interface Security {
        String[] PERMIT_ALL_URL = {
                "/", "/login", "/resource/**"
        };
        String[] WEB_URL_REQUIRE_ROLE_MANAGER = {
                "/admin/building-create", "/admin/user**", "/admin/customer-create"
        };
        String[] API_GET_METHOD_REQUIRE_ROLE_MANAGER_URL = {
                "/api/user/staffs"
        };
        String[] API_POST_METHOD_REQUIRE_ROLE_MANAGER_URL = {
                "/api/building", "/api/building/assignment",
                "/api/customer", "/api/customer/assignment",
                "/api/user"
        };
        String[] API_PUT_METHOD_REQUIRE_ROLE_MANAGER_URL = {
                "/api/user", "/api/user/password/{id}/reset"
        };
        String[] API_DELETE_METHOD_REQUIRE_ROLE_MANAGER_URL = {
                "/api/building", "/api/customer", "/api/user"
        };

    }

}
