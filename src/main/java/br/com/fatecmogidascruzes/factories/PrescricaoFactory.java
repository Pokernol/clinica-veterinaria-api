package br.com.fatecmogidascruzes.factories;

import br.com.fatecmogidascruzes.entities.Prescricao;
import br.com.fatecmogidascruzes.builders.PrescricaoBuilder;

public class PrescricaoFactory extends BaseFactory<Prescricao> {

    @Override
    public Prescricao create() {
        return new PrescricaoBuilder()
                .withMedicamento("Antibiótico")
                .withDosagem("500mg")
                .withFrequencia("2 vezes ao dia por 7 dias")
                .build();
    }
}