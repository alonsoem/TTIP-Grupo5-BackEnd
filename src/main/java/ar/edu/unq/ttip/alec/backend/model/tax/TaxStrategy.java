package ar.edu.unq.ttip.alec.backend.model.tax;

import ar.edu.unq.ttip.alec.backend.model.enumClasses.Apartado;
import ar.edu.unq.ttip.alec.backend.model.TaxResult;

import java.math.BigDecimal;

public interface TaxStrategy {

    TaxResult calculateWith(BigDecimal amount, Apartado apartado);
}
