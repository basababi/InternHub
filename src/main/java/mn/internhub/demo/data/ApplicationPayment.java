package mn.internhub.demo.data;

import jakarta.persistence.*;
import lombok.*;
import mn.internhub.demo.data.enums.PaymentStatus;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "applicationPayemnt")
public class ApplicationPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long paymentId;
    private Long applicationId;
    private Long studentId;
    private Integer amount;
    private String currency;
    private PaymentStatus paymentStatus;
    private Long qpayInvoicedId;
    private Long qPayPaymentId;
    private LocalDate expiredAt;
    private LocalDate paidAt;
    private LocalDate createdAt;
}
