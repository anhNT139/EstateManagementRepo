package com.kepler.controller.admin;

import com.kepler.dto.customer.CustomerDTO;
import com.kepler.dto.customer.CustomerSearchRequest;
import com.kepler.service.ICustomerService;
import com.kepler.service.ITransactionService;
import com.kepler.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller(value = "customerControllerOfAdmin")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    private final IUserService userService;

    private final ITransactionService transactionService;

    @GetMapping(value = "/admin/customer-list")
    public ModelAndView getCustomers(@ModelAttribute @Valid CustomerSearchRequest customerSearchRequest) {

        ModelAndView mav = new ModelAndView("admin/customer/list");

        mav.addObject("pageableCustomers", customerService.searchCustomers(customerSearchRequest));
        mav.addObject("staffsMap", userService.getStaffsMap());
        mav.addObject("statusMap", customerService.getCustomerStatusMap());
        mav.addObject("modelSearch", customerSearchRequest);

        return mav;
    }

    @GetMapping(value = "/admin/customer-create")
    public ModelAndView createCustomer() {

        ModelAndView mav = new ModelAndView("admin/customer/edit");

        mav.addObject("title","Thêm mới khách hàng");
        mav.addObject("submitMethod", "POST");
        mav.addObject("customer", new CustomerDTO());
        mav.addObject("submitText", "Thêm mới khách hàng");
        mav.addObject("statusMap", null);

        return mav;
    }

    @GetMapping(value = "/admin/customer-edit")
    @PreAuthorize("hasRole('manager') " + //equivalent to T(com.kepler.constant.SystemConstant$User).ROLE_MANAGER
            "OR (hasRole('staff') AND @userService.checkUserHasManagedCustomer(authentication.principal.id ,#customerId))")
    public ModelAndView updateCustomer(@RequestParam(name = "id") Long customerId) {

        CustomerDTO customerDTO = customerService.findById(customerId);
        if (customerDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        ModelAndView mav = new ModelAndView("admin/customer/edit");

        mav.addObject("customer", customerDTO);
        mav.addObject("title","Cập nhật thông tin");
        mav.addObject("submitMethod", "PUT");
        mav.addObject("submitText", "Cập nhật khách hàng");
        mav.addObject("statusMap", customerService.getCustomerStatusMap());
        mav.addObject("transactions", transactionService.getTransactions(customerId));
        mav.addObject("transactionTypes", transactionService.getTransactionTypeMap());

        return mav;
    }

}
