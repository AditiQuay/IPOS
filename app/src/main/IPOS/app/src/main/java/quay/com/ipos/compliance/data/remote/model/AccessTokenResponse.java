package quay.com.ipos.compliance.data.remote.model;

public class AccessTokenResponse {

    public int Code;
    public UserAccess UserAccess;

    public class UserAccess {
        public int ID;
        public String UserEmailID;
        public String UserPwd;
        public String AccessToken;
        public String EmpCode;
        public boolean IsActive;
        public int RoleCode;
    }
}

