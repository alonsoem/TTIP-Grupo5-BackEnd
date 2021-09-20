package ar.edu.unq.ttip.alec.backend.model.tax;

import ar.edu.unq.ttip.alec.backend.model.Apartado;
import ar.edu.unq.ttip.alec.backend.model.FrontUser;
import ar.edu.unq.ttip.alec.backend.model.Province;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Entity;

@Entity
public class IVAExterior extends Tax {

    public IVAExterior(String name, Double tax) {
        super(name,tax);
    }
    protected IVAExterior(){}


    @Override
    public Double calculateWith(Double amount, Apartado apartado) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FrontUser userDetails = (FrontUser) auth.getPrincipal();

        if (userDetails.isResponsableInscripto() || userDetails.getProvince() == Province.TIERRA_DEL_FUEGO){
            return 0.0;
        }
        if (apartado==Apartado.APARTADOA) {
            return this.getRate()/100*amount;
        }
        if (apartado==Apartado.APARTADOB && amount<10) {
            return 0.0;
        }else{
            return 0.0;
        }
    }
}
