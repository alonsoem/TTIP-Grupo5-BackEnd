package ar.edu.unq.ttip.alec.backend.model.tax;

import ar.edu.unq.ttip.alec.backend.model.Apartado;
import ar.edu.unq.ttip.alec.backend.model.FrontUser;
import ar.edu.unq.ttip.alec.backend.model.Province;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class IVAExterior extends Tax {

    public IVAExterior(String name, BigDecimal tax) {
        super(name,tax);
    }
    protected IVAExterior(){}


    @Override
    public BigDecimal calculateWith(BigDecimal amount, Apartado apartado) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FrontUser userDetails = (FrontUser) auth.getPrincipal();

        if (userDetails.isResponsableInscripto() || userDetails.getProvince() == Province.TIERRA_DEL_FUEGO){
        }
        if (apartado==Apartado.APARTADOA) {
            return amount.multiply(this.getRate().divide(BigDecimal.valueOf(100)));
        }
        if (apartado==Apartado.APARTADOB && amount.compareTo(BigDecimal.TEN)==-1) {
            return BigDecimal.ZERO;
        }else{
            return BigDecimal.ZERO;
        }
    }
}
