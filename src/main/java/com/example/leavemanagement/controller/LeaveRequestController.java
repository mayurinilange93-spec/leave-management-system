package com.example.leavemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.leavemanagement.model.LeaveRequest;
import com.example.leavemanagement.service.LeaveRequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leave-requests")
@Tag(name = "Leave Request Management", description = "APIs for managing leave requests")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @Autowired
    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    // GET /api/leave-requests - list every leave request in the system
    @GetMapping
    @Operation(summary = "Get all leave requests", description = "Retrieve all leave requests in the system")
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }

    // GET /api/leave-requests/1 - get a single leave request
    @GetMapping("/{id}")
    @Operation(summary = "Get leave request by ID", description = "Retrieve a specific leave request by ID")
    public LeaveRequest getLeaveRequestById(@PathVariable Long id) {
        return leaveRequestService.getLeaveRequestById(id);
    }

    // GET /api/leave-requests/employee/3 - all leave requests for employee 3
    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get leave requests for employee", description = "Retrieve all leave requests for a specific employee")
    public List<LeaveRequest> getLeaveRequestsForEmployee(@PathVariable Long employeeId) {
        return leaveRequestService.getLeaveRequestsForEmployee(employeeId);
    }

    // POST /api/leave-requests/apply/3 - employee 3 applies for leave
    @PostMapping("/apply/{employeeId}")
    @Operation(summary = "Apply for leave", description = "Submit a new leave request for an employee")
    public LeaveRequest applyForLeave(@PathVariable Long employeeId,
                                       @Valid @RequestBody LeaveRequest leaveRequest) {
        return leaveRequestService.applyForLeave(employeeId, leaveRequest);
    }

    // PUT /api/leave-requests/1/approve - approve a pending request
    @PutMapping("/{id}/approve")
    @Operation(summary = "Approve leave request", description = "Approve a pending leave request")
    public LeaveRequest approveLeave(@PathVariable Long id) {
        return leaveRequestService.approveLeave(id);
    }

    // PUT /api/leave-requests/1/reject - reject a pending request
    @PutMapping("/{id}/reject")
    @Operation(summary = "Reject leave request", description = "Reject a pending leave request")
    public LeaveRequest rejectLeave(@PathVariable Long id) {
        return leaveRequestService.rejectLeave(id);
    }

    // DELETE /api/leave-requests/1
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete leave request", description = "Delete a leave request from the system")
    public String deleteLeaveRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
        return "Leave request with id " + id + " deleted successfully";
    }
}
