package com.service.dida.user.wallet;

import com.service.dida.user.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;
    private String address;
    private double klay;
    private double dida;

    @OneToOne(mappedBy = "wallet")
    private User user;

    public void updateKlay(double klay) {
        this.klay = klay;
    }
    public void updateDida(double dida) {
        this.dida = dida;
    }

}
