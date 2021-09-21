package ar.edu.unq.ttip.alec.backend.model.tax;

import ar.edu.unq.ttip.alec.backend.model.Apartado;

import java.math.BigDecimal;

public interface TaxStrategy {

    BigDecimal calculateWith(BigDecimal amount, Apartado apartado);
}
