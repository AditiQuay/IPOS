package quay.com.ipos.compliance.interfaces;


import quay.com.ipos.compliance.data.local.entity.Employee;

/**
 * Created by deepak.kumar1 on 01-05-2018.
 */
public interface UserSelectionListener {
    void onUserSlected(Employee employee);
}
