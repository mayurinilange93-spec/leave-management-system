package com.example.leavemanagement.service;

import com.example.leavemanagement.model.Employee;
import com.example.leavemanagement.model.LeaveRequest;
import com.example.leavemanagement.model.LeaveStatus;
import com.example.leavemanagement.repository.EmployeeRepository;
import com.example.leavemanagement.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository,
                                EmployeeRepository employeeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public LeaveRequest getLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + id));
    }

    public List<LeaveRequest> getLeaveRequestsForEmployee(Long employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId);
    }

    // Applying for leave: we just save it with PENDING status.
    // We don't touch the employee's leave balance until it's approved.
    public LeaveRequest applyForLeave(Long employeeId, LeaveRequest leaveRequest) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        leaveRequest.setEmployee(employee);
        leaveRequest.setStatus(LeaveStatus.PENDING);
        return leaveRequestRepository.save(leaveRequest);
    }

    // Approving leave: check the employee has enough balance, then deduct it.
    public LeaveRequest approveLeave(Long leaveRequestId) {
        LeaveRequest leaveRequest = getLeaveRequestById(leaveRequestId);

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Only pending leave requests can be approved");
        }

        Employee employee = leaveRequest.getEmployee();
        long daysRequested = leaveRequest.getNumberOfDays();

        if (employee.getLeaveBalance() < daysRequested) {
            throw new RuntimeException("Employee does not have enough leave balance");
        }

        employee.setLeaveBalance((int) (employee.getLeaveBalance() - daysRequested));
        employeeRepository.save(employee);

        leaveRequest.setStatus(LeaveStatus.APPROVED);
        return leaveRequestRepository.save(leaveRequest);
    }

    public LeaveRequest rejectLeave(Long leaveRequestId) {
        LeaveRequest leaveRequest = getLeaveRequestById(leaveRequestId);

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Only pending leave requests can be rejected");
        }

        leaveRequest.setStatus(LeaveStatus.REJECTED);
        return leaveRequestRepository.save(leaveRequest);
    }

    public void deleteLeaveRequest(Long id) {
        LeaveRequest existing = getLeaveRequestById(id);
        leaveRequestRepository.delete(existing);
    }
}
