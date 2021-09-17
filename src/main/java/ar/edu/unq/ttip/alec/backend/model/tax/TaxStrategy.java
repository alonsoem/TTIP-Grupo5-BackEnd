package ar.edu.unq.ttip.alec.backend.model.tax;

public interface TaxStrategy {

    Double calculateWith(Double amount, String description);
}
