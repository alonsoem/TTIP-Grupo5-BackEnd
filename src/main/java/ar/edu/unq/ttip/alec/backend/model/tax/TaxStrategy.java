package ar.edu.unq.ttip.alec.backend.model.tax;

import ar.edu.unq.ttip.alec.backend.model.Apartado;

public interface TaxStrategy {

    Double calculateWith(Double amount, Apartado apartado);
}
