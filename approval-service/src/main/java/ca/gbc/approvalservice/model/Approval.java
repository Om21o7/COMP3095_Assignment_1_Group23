package ca.gbc.approvalservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "approvals") // Table name in PostgreSQL
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID
    private Long id;

    private String eventId;    // ID of the associated event
    private String approver;   // Person who approves/rejects
    private String status;     // e.g., "Pending", "Approved", "Rejected"
    private String notes;      // Any additional comments from the approver
}
